package com.pandora.rpc.registry;

import com.pandora.rpc.protocol.RpcProtocol;

public interface RegistryService {


    /**
     * 注册服务
     *
     * @param rpcProtocol
     */
    void registerService(RpcProtocol rpcProtocol);
}
