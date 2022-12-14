package com.pandora.mysql.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pandora.mysql.model.NodeInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;

public interface NodeInfoMapper extends BaseMapper<NodeInfo> {

    @Select("SELECT * FROM node_info WHERE group_name = #{groupName} AND node_ip = #{nodeIp}")
    NodeInfo checkNode(@Param("groupName") String groupName,@Param("nodeIp") String nodeIp);

    @Update("update node_info set heartbeat=#{heartbeat} where group_name=#{groupName} and node_ip=#{nodeIp}")
    boolean heartbeat(@Param("groupName") String groupName,@Param("nodeIp") String nodeIp, @Param("heartbeat") Date heartbeat);

    @Select("select * from node_info where group_name=#{groupName} and off_line_ip is null and UNIX_TIMESTAMP(CURRENT_TIMESTAMP()) - UNIX_TIMESTAMP(heartbeat)>#{heartbeatTimeOut}")
    List<NodeInfo> queryAllHeartbeatTimeOutNodeInfo(@Param("groupName") String groupName,@Param("heartbeatTimeOut") Long heartbeatTimeOut);

    @Update("update node_info set off_line_ip=#{offLineNodeIp},is_active=0 where group_name=#{groupName} and node_ip=#{nodeIp} and off_line_ip is null")
    boolean offLineHeartbeatNode(@Param("groupName") String groupName,@Param("offLineNodeIp") String offLineNodeIp,@Param("nodeIp") String nodeIp);

    @Select("select count(*) from node_info where group_name=#{groupName} and is_active=1")
    int activeNodeTotalCount(@Param("groupName") String groupName);
}
