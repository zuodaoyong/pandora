package com.pandora.core.manager;

import com.pandora.core.manager.node.NodeManager;
import com.pandora.core.manager.partition.PartitionManager;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 分布式管理服务启动器
 */
@Component
public class SmartDistributedTaskManagerStarter implements InitializingBean {

    @Resource
    private NodeManager nodeManager;

    @Resource
    private PartitionManager partitionManager;

    private void startNodeManager(){
        nodeManager.prePare();
        nodeManager.execute();
    }

    private void startPartitionManager(){
        partitionManager.prePare();
        partitionManager.execute();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        startNodeManager();
        startPartitionManager();
    }
}
