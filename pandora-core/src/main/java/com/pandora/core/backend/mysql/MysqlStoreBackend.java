package com.pandora.core.backend.mysql;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
                partitionInfo.setPartition(i);
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

    /**
     * 判断是否已经分配过该分区
     *
     * @param groupName
     * @param partition
     * @return
     */
    private boolean checkPartition(String groupName, Integer partition) {
        QueryWrapper<PartitionInfo> queryWrapper = new QueryWrapper<PartitionInfo>().eq("group_name", groupName)
                .eq("partition", partition);
        Long count = partitionInfoMapper.selectCount(queryWrapper);
        return count != null && count > 0 ? true : false;
    }
}
