package com.pandora.mysql.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 注册中心
 */
@TableName("registry_info")
@Data
public class RegistryInfo {

    private Long id;

    private String node;

    private int port;

    private String serviceName;

    private String serviceKey;

    private Date gmtCreate;

    private Date gmtModify;

}
