package com.pandora.rpc.server.handler;

import com.pandora.rpc.server.codec.Beat;
import com.pandora.rpc.server.codec.RpcRequest;
import com.pandora.rpc.server.codec.RpcResponse;
import com.pandora.utils.CommonUtils;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.reflect.FastClass;

import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
public class RpcServerHandler extends SimpleChannelInboundHandler<RpcRequest> {

    private final Map<String, Object> handlerMap;

    private final ThreadPoolExecutor serverHandlerPool;


    public RpcServerHandler(Map<String, Object> handlerMap, final ThreadPoolExecutor threadPoolExecutor) {
        this.handlerMap = handlerMap;
        this.serverHandlerPool = threadPoolExecutor;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcRequest request) throws Exception {

        if (Beat.BEAT_ID.equalsIgnoreCase(request.getRequestId())) {
            log.info("Server read heartbeat ping");
            return;
        }

        serverHandlerPool.execute(new Runnable() {
            @Override
            public void run() {
                log.info("Receive request " + request.getRequestId());
                RpcResponse response = new RpcResponse();
                response.setRequestId(request.getRequestId());
                try {
                    Object result = handle(request);
                    response.setResult(result);
                } catch (Throwable t) {
                    response.setError(t.toString());
                    log.error("RPC Server handle request error", t);
                }
                ctx.writeAndFlush(response).addListener(new ChannelFutureListener() {
                    @Override
                    public void operationComplete(ChannelFuture channelFuture) throws Exception {
                        log.info("Send response for request " + request.getRequestId());
                    }
                });
            }
        });

    }

    private Object handle(RpcRequest request) throws Throwable {
        String className = request.getClassName();
        String version = request.getVersion();
        String serviceKey = CommonUtils.makeServiceKey(className, version);
        Object serviceBean = handlerMap.get(serviceKey);
        if (serviceBean == null) {
            log.error("Can not find service implement with interface name: {} and version: {}", className, version);
            return null;
        }

        Class<?> serviceClass = serviceBean.getClass();
        String methodName = request.getMethodName();
        Class<?>[] parameterTypes = request.getParameterTypes();
        Object[] parameters = request.getParameters();

        log.debug(serviceClass.getName());
        log.debug(methodName);
        for (int i = 0; i < parameterTypes.length; ++i) {
            log.debug(parameterTypes[i].getName());
        }

        for (int i = 0; i < parameters.length; ++i) {
            log.debug(parameters[i].toString());
        }

        // Cglib reflect
        FastClass serviceFastClass = FastClass.create(serviceClass);
        // for higher-performance
        int methodIndex = serviceFastClass.getIndex(methodName, parameterTypes);
        return serviceFastClass.invoke(methodIndex, serviceBean, parameters);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.warn("Server caught exception: " + cause.getMessage());
        ctx.close();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            ctx.channel().close();
            log.warn("Channel idle in last {} seconds, close it", Beat.BEAT_TIMEOUT);
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }
}
