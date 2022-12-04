package com.pandora.mysql.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pandora.mysql.model.PartitionInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface PartitionInfoMapper extends BaseMapper<PartitionInfo> {

    @Update("update partition_info set node_name=null where group_name=#{groupName} and node_name=#{nodeName} " +
            "and partition in <foreach collection='holdingPartitions' item='partition' index='index' open='(' separator=',' close =')'>" +
            "#{partition}" +
            "</foreach>")
    boolean releaseNodeHoldingPartition(@Param("groupName") String groupName, @Param("nodeName") String nodeName, List<Integer> holdingPartitions);

    @Select("select partition from partition_info where group_name=#{groupName} and node_name=#{nodeName}")
    List<Integer> queryNodeHoldingPartition(@Param("groupName") String groupName, @Param("nodeName") String nodeName);
}
