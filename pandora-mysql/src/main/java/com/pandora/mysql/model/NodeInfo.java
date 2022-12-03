package com.pandora.mysql.model;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@TableName("node_info")
@Data
public class NodeInfo extends BaseInfo{

    /**
     * 环境
     */
    private String groupName;

    /**
     * 节点名称
     */
    private String nodeName;

    /**
     * 节点IP
     */
    private String nodeIp;

    /**
     * 心跳时间
     */
    private Date heartbeat;

    /**
     * 是否存活
     */
    private boolean isActive;

    /**
     * 当node_ip不能心跳时，由offline_ip来处理后续事情
     */
    private String offLineIp;

}
