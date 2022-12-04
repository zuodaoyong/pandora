package com.pandora.core.manager.partition.executor;

import com.pandora.core.backend.MetaStoreBackend;
import com.pandora.core.manager.BaseDistributedTaskManager;
import com.pandora.core.manager.node.NodeManager;
import com.pandora.core.manager.partition.PartitionManager;
import com.pandora.core.param.DistributedTaskConfigProperties;
import com.pandora.mysql.model.NodeInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * 分区负载均衡
 */
@Slf4j
@Component
public class PartitionReBalanceExecutor implements Runnable {

    @Resource
    private DistributedTaskConfigProperties distributedTaskConfigProperties;

    @Resource
    private PartitionManager partitionManager;

    @Resource
    private MetaStoreBackend metaStoreBackend;

    @Resource
    private NodeManager nodeManager;

    @Override
    public void run() {
        while (BaseDistributedTaskManager.StatusEnum.RUNNING == partitionManager.getStatus()) {
            try {
                NodeInfo nodeInfo = nodeManager.getNodeInfo();
                String nodeName = nodeInfo.getNodeName();
                Integer maxPartitionCount = calcHoldingMaxPartitionCount();
                log.info("maxPartitionCount is {}", maxPartitionCount);
                if (maxPartitionCount.intValue() == -1) {
                    return;
                }
                boolean hasIdlePartitionSlot = hasIdlePartitionSlot(nodeName, maxPartitionCount);
                log.info("hasIdlePartitionSlot is {}", hasIdlePartitionSlot);
                if (hasIdlePartitionSlot) {
                    log.info("start tryAcquirePartition ...");
                    tryAcquirePartition(nodeName);
                }
            } catch (Exception e) {
                log.error(PartitionReBalanceExecutor.class.getSimpleName() + "heartbeat error", e);
            } finally {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    log.error(PartitionReBalanceExecutor.class.getSimpleName() + "sleep error", e);
                }
            }
        }
    }

    /**
     * 是否还有空闲的分区槽位
     *
     * @param nodeName
     * @param maxPartitionCount
     * @return
     */
    private boolean hasIdlePartitionSlot(String nodeName, Integer maxPartitionCount) {
        List<Integer> holdingPartitions = metaStoreBackend.queryNodeHoldingPartition(nodeName);
        return maxPartitionCount > 0 && holdingPartitions.size() < maxPartitionCount;
    }

    /**
     * 计算每个节点最多获取分区数
     *
     * @return
     */
    private Integer calcHoldingMaxPartitionCount() {
        Integer totalPartition = distributedTaskConfigProperties.getTotalPartition();
        Integer activeNodeTotalCount = nodeManager.getActiveNodeTotalCount();
        if (activeNodeTotalCount == null) {
            return -1;
        }
        return (int) Math.ceil(totalPartition * 1.0 / activeNodeTotalCount);
    }

    /**
     * 尝试申请分区
     */
    private void tryAcquirePartition(String nodeName) {
        List<Integer> idlePartitions = metaStoreBackend.queryIdlePartitions(1);
        if (CollectionUtils.isEmpty(idlePartitions)) {
            return;
        }
        for (Integer partition : idlePartitions) {
            metaStoreBackend.tryAcquirePartition(nodeName, partition);
        }
    }
}
