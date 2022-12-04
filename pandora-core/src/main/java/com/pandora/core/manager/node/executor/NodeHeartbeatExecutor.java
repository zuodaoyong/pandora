package com.pandora.core.manager.node.executor;

import com.pandora.core.backend.MetaStoreBackend;
import com.pandora.core.manager.BaseDistributedTaskManager;
import com.pandora.core.manager.node.NodeManager;
import com.pandora.core.param.DistributedTaskConfigProperties;
import com.pandora.mysql.model.NodeInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class NodeHeartbeatExecutor implements Runnable {

    @Resource
    private MetaStoreBackend metaStoreBackend;

    @Resource
    private NodeManager nodeManager;

    @Resource
    private DistributedTaskConfigProperties distributedTaskConfigProperties;

    @Override
    public void run() {
        while (BaseDistributedTaskManager.StatusEnum.RUNNING == nodeManager.getStatus()) {
            String groupName = distributedTaskConfigProperties.getGroupName();
            try {
                NodeInfo nodeInfo = nodeManager.nodeInfo();
                log.info("{} heartbeat start...", nodeInfo.getNodeName());
                //1、触发心跳
                heartbeat(nodeInfo);
                //2、处理心跳超时的节点
                handleHeartbeatTimeOutNode(nodeInfo);
                log.info("{} heartbeat end...", nodeInfo.getNodeName());

            } catch (Exception e) {
                log.error(NodeHeartbeatExecutor.class.getSimpleName() + "heartbeat error", e);
            } finally {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    log.error(NodeHeartbeatExecutor.class.getSimpleName() + "sleep error", e);
                }
            }
        }

    }

    /**
     * 触发心跳
     */
    private void heartbeat(NodeInfo nodeInfo) {
        metaStoreBackend.heartbeat(nodeInfo.getNodeIp(), new Date());
    }

    /**
     * 处理心跳超时的节点
     */
    @Transactional(rollbackFor = Exception.class)
    public void handleHeartbeatTimeOutNode(NodeInfo nodeInfo) {
        List<NodeInfo> heartbeatTimeOutNodeInfoList = metaStoreBackend.queryAllHeartbeatTimeOutNodeInfo();
        if (CollectionUtils.isEmpty(heartbeatTimeOutNodeInfoList)) {
            return;
        }
        for (NodeInfo timeOutNodeInfo : heartbeatTimeOutNodeInfoList) {
            boolean offLineHeartbeatNode = metaStoreBackend.offLineHeartbeatNode(nodeInfo.getNodeIp(), timeOutNodeInfo.getNodeIp());
            if (offLineHeartbeatNode) {
                List<Integer> holdingPartitions=metaStoreBackend.queryNodeHoldingPartition(timeOutNodeInfo.getNodeName());
                if(CollectionUtils.isEmpty(holdingPartitions)){
                    return;
                }
                //释放节点持有的分区信息
                metaStoreBackend.releaseNodeHoldingPartition(timeOutNodeInfo.getNodeName(),holdingPartitions);
            }
        }
    }
}
