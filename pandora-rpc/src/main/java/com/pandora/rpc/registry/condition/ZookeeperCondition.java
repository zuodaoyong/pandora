package com.pandora.rpc.registry.condition;

import com.pandora.rpc.param.RpcConfigProperties;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class ZookeeperCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {

        Environment environment = context.getEnvironment();

        String registryStore = environment.getProperty("rpc.registry.store");

        RpcConfigProperties.Registry.Store store = RpcConfigProperties.Registry.Store.valueOf(registryStore);

        return RpcConfigProperties.Registry.Store.Zookeeper == store ? true : false;
    }
}
