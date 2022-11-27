package com.pandora.core.param;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "distributed")
@Component
@Data
public class DistributedTaskConfigProperties {

    /**
     * 环境隔离
     */
    private String groupName;

    /**
     * 分区数量
     */
    private Integer totalPartition;

    private MetaStore metaStore;

    public enum MetaStore {
        Zookeeper,

        Mysql
    }

}
