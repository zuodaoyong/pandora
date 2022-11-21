package com.pandora.core.param;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "log")
@Component
@Data
public class LogConfigProperties {

    /**
     * 日志根目录配置
     */
    private String basePath;





}
