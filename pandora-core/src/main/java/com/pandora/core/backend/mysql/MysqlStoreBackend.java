package com.pandora.core.backend.mysql;


import com.pandora.core.backend.MetaStoreBackend;
import com.pandora.core.condition.MysqlCondition;
import com.pandora.core.param.DistributedTaskConfigProperties;
import com.pandora.mysql.Mapper.NodeInfoMapper;
import com.pandora.mysql.Mapper.PartitionInfoMapper;
import com.pandora.mysql.model.NodeInfo;
import com.pandora.mysql.model.PartitionInfo;
import com.pandora.utils.IpUtils;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Component
@Conditional(MysqlCondition.class)
public class MysqlStoreBackend implements MetaStoreBackend {


    @Resource
    private DistributedTaskConfigProperties distributedTaskConfigProperties;

    @Resource
    private NodeInfoMapper nodeInfoMapper;

    @Resource
    private PartitionInfoMapper partitionInfoMapper;

    @Override
    public NodeInfo registerNodeInfo() {
        NodeInfo dbNodeInfo = checkNode();
        if (dbNodeInfo!=null) {
            return dbNodeInfo;
        }
        Date now = new Date();
        NodeInfo nodeInfo = new NodeInfo();
        nodeInfo.setGroupName(distributedTaskConfigProperties.getGroupName());
        nodeInfo.setNodeIp(IpUtils.getIp());
        nodeInfo.setNodeName(IpUtils.getNodeName());
        nodeInfo.setActive(true);
        nodeInfo.setHeartbeat(now);
        nodeInfoMapper.insert(nodeInfo);
        return nodeInfo;
    }

    @Override
    public List<NodeInfo> queryAllHeartbeatTimeOutNodeInfo() {
        String groupName = distributedTaskConfigProperties.getGroupName();
        Long heartbeatTimeOut = distributedTaskConfigProperties.getHeartbeatTimeOut();
        return nodeInfoMapper.queryAllHeartbeatTimeOutNodeInfo(groupName, heartbeatTimeOut);
    }

    @Override
    public boolean heartbeat(String nodeIp, Date hearbeat) {
        String groupName = distributedTaskConfigProperties.getGroupName();
        return nodeInfoMapper.heartbeat(groupName, nodeIp, hearbeat);
    }

    @Override
    public boolean offLineHeartbeatNode(String offLineNodeIp, String nodeIp) {
        String groupName = distributedTaskConfigProperties.getGroupName();
        return nodeInfoMapper.offLineHeartbeatNode(groupName, offLineNodeIp, nodeIp);
    }

    /**
     * 注册分区信息
     */
    @Override
    public void registerPartitionInfo() {
        Integer totalPartition = distributedTaskConfigProperties.getTotalPartition();
        String groupName = distributedTaskConfigProperties.getGroupName();
        for (int i = 1; i <= totalPartition; i++) {
            boolean checkPartition = checkPartition(groupName, i);
            if (!checkPartition) {
                PartitionInfo partitionInfo = new PartitionInfo();
                partitionInfo.setGroupName(groupName);
                partitionInfo.setPartitionNo(i);
                partitionInfo.setNodeName(null);
                partitionInfoMapper.insert(partitionInfo);
            }
        }
    }

    @Override
    public boolean releaseNodeHoldingPartition(String nodeName, List<Integer> holdingPartitions) {
        String groupName = distributedTaskConfigProperties.getGroupName();
        return partitionInfoMapper.releaseNodeHoldingPartition(groupName, nodeName, holdingPartitions);
    }

    @Override
    public List<Integer> queryNodeHoldingPartition(String nodeName) {
        String groupName = distributedTaskConfigProperties.getGroupName();
        return partitionInfoMapper.queryNodeHoldingPartition(groupName, nodeName);
    }

    @Override
    public Integer activeNodeTotalCount() {
        String groupName = distributedTaskConfigProperties.getGroupName();
        return nodeInfoMapper.activeNodeTotalCount(groupName);
    }

    @Override
    public List<Integer> queryIdlePartitions(Integer limit) {
        String groupName = distributedTaskConfigProperties.getGroupName();
        return partitionInfoMapper.queryIdlePartitions(groupName, limit);
    }

    @Override
    public Integer tryAcquirePartition(String nodeName, Integer partition) {
        String groupName = distributedTaskConfigProperties.getGroupName();
        return partitionInfoMapper.tryAcquirePartition(groupName, nodeName, partition);
    }

    /**
     * 判断是否已经分配过该分区
     *
     * @param groupName
     * @param partition
     * @return
     */
    private boolean checkPartition(String groupName, Integer partition) {
        Integer checkPartition = partitionInfoMapper.checkPartition(groupName, partition);
        return checkPartition != null && checkPartition > 0 ? true : false;
    }

    /**
     * 检查是否注册过该节点
     *
     * @return
     */
    private NodeInfo checkNode() {
        String groupName = distributedTaskConfigProperties.getGroupName();
        NodeInfo nodeInfo = nodeInfoMapper.checkNode(groupName, IpUtils.getIp());
        return nodeInfo;
    }
}
