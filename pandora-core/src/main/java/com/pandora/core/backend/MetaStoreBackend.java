package com.pandora.core.backend;

public interface MetaStoreBackend {

    /**
     * 注册节点
     */
    void registerNodeInfo();

    /**
     * 注册分区信息
     */
    void registerPartitionInfo();
}
