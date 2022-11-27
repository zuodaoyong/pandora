package com.pandora.mysql.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("partition_info")
@Data
public class PartitionInfo extends BaseInfo {

    /**
     * 环境
     */
    private String groupName;

    /**
     * 点名称
     */
    private String nodeName;

    /**
     * 分区号
     */
    private Integer partition;
}
