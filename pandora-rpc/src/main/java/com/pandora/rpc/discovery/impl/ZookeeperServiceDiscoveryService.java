package com.pandora.rpc.discovery.impl;

import com.pandora.rpc.condition.ZookeeperCondition;
import com.pandora.rpc.discovery.ServiceDiscovery;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

@Component
@Conditional(ZookeeperCondition.class)
public class ZookeeperServiceDiscoveryService implements ServiceDiscovery {

    @Override
    public void discoveryService() {

    }
}
