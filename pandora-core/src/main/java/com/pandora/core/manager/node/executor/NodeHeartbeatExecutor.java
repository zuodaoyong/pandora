package com.pandora.core.manager.node.executor;

import com.pandora.core.backend.MetaStoreBackend;
import com.pandora.core.manager.BaseDistributedTaskManager;
import com.pandora.core.manager.node.NodeManager;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class NodeHeartbeatExecutor implements Runnable{

    @Resource
    private MetaStoreBackend metaStoreBackend;

    @Resource
    private NodeManager nodeManager;

    @Override
    public void run() {

        if(BaseDistributedTaskManager.StatusEnum.RUNNING==nodeManager.getStatus()){


        }

    }
}
