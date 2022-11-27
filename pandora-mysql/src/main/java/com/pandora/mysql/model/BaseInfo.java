package com.pandora.mysql.model;

import lombok.Data;

import java.util.Date;

@Data
public class BaseInfo {

    /**
     * 主键
     */
    private Long id;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 更新时间
     */
    private Date gmtModify;
}
