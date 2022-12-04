package com.pandora.core.manager.node;

import com.pandora.common.ThreadPoolUtils;
import com.pandora.core.backend.MetaStoreBackend;
import com.pandora.core.manager.BaseDistributedTaskManager;
import com.pandora.core.manager.node.executor.NodeHeartbeatExecutor;
import com.pandora.mysql.model.NodeInfo;
import lombok.Data;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.concurrent.ThreadPoolExecutor;

@Component
@Data
public class NodeManager extends BaseDistributedTaskManager {

    @Resource
    private MetaStoreBackend metaStoreBackend;

    @Resource
    private NodeHeartbeatExecutor nodeHeartbeatExecutor;

    private NodeInfo nodeInfo;

    /**
     * 存活的所有节点数量
     */
    private Integer activeNodeTotalCount;

    private ThreadPoolExecutor threadPool;


    @Override
    public void prePare() {
        super.prePare();
        threadPool = ThreadPoolUtils.createThreadPool(NodeManager.class.getSimpleName(), 1, 1);
    }

    @Override
    public void execute() {
        //注册节点
        nodeInfo = metaStoreBackend.registerNodeInfo();
        //心跳管理器
        threadPool.submit(nodeHeartbeatExecutor);

    }

}
