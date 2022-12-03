package com.pandora.core.backend;

import com.pandora.mysql.model.NodeInfo;

import java.util.Date;
import java.util.List;

public interface MetaStoreBackend {

    /**
     * 注册节点
     */
    NodeInfo registerNodeInfo();


    /**
     * 查询所有心跳超时的节点
     *
     * @param heartbeatTimeOut
     * @return
     */
    List<NodeInfo> queryAllHeartbeatTimeOutNodeInfo(Long heartbeatTimeOut);

    /**
     * 心跳
     *
     * @param ip
     * @param hearbeat
     * @return
     */
    boolean heartbeat(String ip, Date hearbeat);

    /**
     * 下线heartbeat超时节点
     *
     * @param offLineNodeIp
     * @param nodeIp
     * @return
     */
    boolean offLineHeartbeatNode(String offLineNodeIp, String nodeIp);

    /**
     * 注册分区信息
     */
    void registerPartitionInfo();
}
