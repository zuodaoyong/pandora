package com.pandora.mysql.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pandora.mysql.model.PartitionInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface PartitionInfoMapper extends BaseMapper<PartitionInfo> {


    @Select("SELECT COUNT( * ) FROM partition_info WHERE group_name = #{groupName} AND partition_no = #{partition}")
    Integer checkPartition(@Param("groupName") String groupName,@Param("partition") Integer partition);

    @Update("update partition_info set node_name=null where group_name=#{groupName} and node_name=#{nodeName} " +
            "and partition_no in <foreach collection='holdingPartitions' item='partition' index='index' open='(' separator=',' close =')'>" +
            "#{partition}" +
            "</foreach>")
    boolean releaseNodeHoldingPartition(@Param("groupName") String groupName, @Param("nodeName") String nodeName, List<Integer> holdingPartitions);

    @Select("select partition_no from partition_info where group_name=#{groupName} and node_name=#{nodeName}")
    List<Integer> queryNodeHoldingPartition(@Param("groupName") String groupName, @Param("nodeName") String nodeName);

    @Select("select partition_no from partition_info where group_name=#{groupName} and node_name is null limit #{limit}")
    List<Integer> queryIdlePartitions(@Param("groupName") String groupName,@Param("limit") Integer limit);

    @Update("update partition_info set node_name=#{nodeName} where node_name is null and partition_no=#{partition}")
    Integer tryAcquirePartition(@Param("groupName") String groupName,@Param("nodeName") String nodeName, @Param("partition") Integer partition);
}
