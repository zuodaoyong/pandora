package com.pandora.rpc.discovery.impl;

import com.pandora.mysql.Mapper.RegistryInfoMapper;
import com.pandora.rpc.condition.MysqlCondition;
import com.pandora.rpc.discovery.ServiceDiscovery;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Conditional(MysqlCondition.class)
public class MysqlServiceDiscoveryService implements ServiceDiscovery {

    @Resource
    private RegistryInfoMapper registryInfoMapper;

    @Override
    public void discoveryService() {




    }
}
