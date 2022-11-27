package com.pandora.core.condition;

import com.pandora.core.param.DistributedTaskConfigProperties;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class ZookeeperCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        Environment environment = context.getEnvironment();

        String registryStore = environment.getProperty("distributed.meta-store");

        DistributedTaskConfigProperties.MetaStore store = DistributedTaskConfigProperties.MetaStore.valueOf(registryStore);

        return DistributedTaskConfigProperties.MetaStore.Zookeeper == store ? true : false;
    }
}
