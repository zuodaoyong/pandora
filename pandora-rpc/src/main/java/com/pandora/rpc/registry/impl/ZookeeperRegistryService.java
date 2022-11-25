package com.pandora.rpc.registry.impl;

import com.pandora.rpc.condition.ZookeeperCondition;
import com.pandora.rpc.protocol.RpcProtocol;
import com.pandora.rpc.registry.RegistryService;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

/**
 * zk注册中心
 */
@Component
@Conditional(ZookeeperCondition.class)
public class ZookeeperRegistryService implements RegistryService {

    @Override
    public void registerService(RpcProtocol rpcProtocol) {



    }
}
