package com.pandora.rpc.registry.impl;

import com.pandora.rpc.registry.RegistryService;
import com.pandora.rpc.registry.condition.ZookeeperCondition;
import com.pandora.rpc.registry.model.RegistryConfig;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

/**
 * zk注册中心
 */
@Component
@Conditional(ZookeeperCondition.class)
public class ZookeeperRegistryService implements RegistryService {

    @Override
    public void registerService(RegistryConfig registryConfig) {



    }
}
