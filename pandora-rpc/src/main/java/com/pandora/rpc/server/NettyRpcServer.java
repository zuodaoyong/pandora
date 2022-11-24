package com.pandora.rpc.server;

import com.pandora.common.ThreadPoolUtils;
import com.pandora.rpc.param.RpcConfigProperties;
import com.pandora.rpc.registry.RegistryService;
import com.pandora.rpc.registry.model.RegistryConfig;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
@Component
public class NettyRpcServer implements RpcServer , InitializingBean {

    private Thread thread;

    @Resource
    private RpcConfigProperties rpcConfigProperties;

    @Resource
    private RegistryConfig registryConfig;

    @Resource
    private RegistryService registerService;

    @Override
    public void start() throws Exception {
        ThreadPoolExecutor threadPoolExecutor = ThreadPoolUtils.createThreadPool(
                NettyRpcServer.class.getSimpleName(), 16, 32);
        thread=new Thread(()->{

            EventLoopGroup bossGroup = new NioEventLoopGroup();
            EventLoopGroup workerGroup = new NioEventLoopGroup();

            Map<String, Object> serviceMap = registryConfig.getServiceMap();
            try {
                ServerBootstrap bootstrap = new ServerBootstrap();
                bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                        .childHandler(new RpcServerInitializer(serviceMap, threadPoolExecutor))
                        .option(ChannelOption.SO_BACKLOG, 128)
                        .childOption(ChannelOption.SO_KEEPALIVE, true);

                String host = registryConfig.getHost();
                int port = registryConfig.getPort();
                ChannelFuture future = bootstrap.bind(host, port).sync();

                if (serviceMap != null) {
                    //注册服务
                    registerService.registerService(registryConfig);
                }
                log.info("Server started on port {}", port);
                future.channel().closeFuture().sync();
            } catch (Exception e) {
                if (e instanceof InterruptedException) {
                    log.info("Rpc server remoting server stop");
                } else {
                    log.error("Rpc server remoting server error", e);
                }
            } finally {
                try {
                    //serviceRegistry.unregisterService();
                    workerGroup.shutdownGracefully();
                    bossGroup.shutdownGracefully();
                } catch (Exception ex) {
                    log.error(ex.getMessage(), ex);
                }
            }
        });
        thread.start();
    }

    @Override
    public void stop() throws Exception {

        if (thread != null && thread.isAlive()) {
            thread.interrupt();
        }

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        start();
    }
}
