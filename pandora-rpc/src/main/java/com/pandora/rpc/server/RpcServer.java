package com.pandora.rpc.server;

public interface RpcServer {


    /**
     * 启动
     *
     * @throws Exception
     */
    void start() throws Exception;

    /**
     * 停止
     *
     * @throws Exception
     */
    void stop() throws Exception;
}
