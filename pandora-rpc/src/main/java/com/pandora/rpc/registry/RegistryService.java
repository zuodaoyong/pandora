package com.pandora.rpc.registry;

import com.pandora.rpc.registry.model.RegistryConfig;

public interface RegistryService {


    /**
     * 注册服务
     *
     * @param registryConfig
     */
    void registerService(RegistryConfig registryConfig);
}
