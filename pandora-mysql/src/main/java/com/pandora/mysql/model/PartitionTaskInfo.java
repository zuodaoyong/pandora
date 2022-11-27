package com.pandora.mysql.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("partition_task_info")
@Data
public class PartitionTaskInfo extends BaseInfo{

    /**
     * 环境
     */
    private String groupName;

    /**
     * 节点名称
     */
    private String nodeName;

    /**
     * 分区号
     */
    private Integer partition;

    /**
     * 任务类型
     */
    private String type;

    /**
     * 业务ID
     */
    private Long bizId;

    /**
     * 优先级
     */
    private Integer priority;

    /**
     * 任务运行状态
     */
    private String status;

}
