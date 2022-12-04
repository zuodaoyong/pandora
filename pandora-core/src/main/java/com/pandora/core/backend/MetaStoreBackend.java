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
     * @return
     */
    List<NodeInfo> queryAllHeartbeatTimeOutNodeInfo();

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


    /**
     * 释放节点持有的分区号
     *
     * @param nodeName
     * @param holdingPartitions
     * @return
     */
    boolean releaseNodeHoldingPartition(String nodeName, List<Integer> holdingPartitions);

    /**
     * 查询Node持有的分区号
     *
     * @param nodeName
     * @return
     */
    List<Integer> queryNodeHoldingPartition(String nodeName);

    /**
     * 获取存活的节点数
     *
     * @return
     */
    Integer activeNodeTotalCount();

    /**
     * 一次性获取空闲的分区号
     *
     * @param limit
     * @return
     */
    List<Integer> queryIdlePartitions(Integer limit);

    /**
     * 申请分配分区
     *
     * @param nodeName
     * @param partition
     * @return
     */
    Integer tryAcquirePartition(String nodeName, Integer partition);
}
