package com.pandora.rpc.registry.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import com.pandora.rpc.param.RpcConfigProperties;


public class MysqlCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {

        RpcConfigProperties rpcConfigProperties = context.getBeanFactory().getBean(RpcConfigProperties.class);

        RpcConfigProperties.Registry.Store store = rpcConfigProperties.getRegistry().getStore();

        return RpcConfigProperties.Registry.Store.Mysql == store ? true : false;
    }
}
