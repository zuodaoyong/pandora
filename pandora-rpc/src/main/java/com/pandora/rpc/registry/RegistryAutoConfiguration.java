package com.pandora.rpc.registry;

import com.pandora.annotation.NettyRpcService;
import com.pandora.rpc.protocol.RpcProtocol;
import com.pandora.utils.CommonUtils;
import com.pandora.utils.IpUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.*;

import java.util.Iterator;
import java.util.Map;

@Slf4j
@Configuration
public class RegistryAutoConfiguration {


    /**
     * 注册服务
     * @return
     */
    @Bean
    public RpcProtocol registryConfig(ApplicationContext applicationContext){
        final RpcProtocol registryConfig =new RpcProtocol();
        ApplicationContextAware applicationContextAware = new ApplicationContextAware() {
            @Override
            public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
                Map<String, Object> serviceBeanMap = applicationContext.getBeansWithAnnotation(NettyRpcService.class);
                if (MapUtils.isNotEmpty(serviceBeanMap)) {
                    Iterator<Map.Entry<String, Object>> iterator = serviceBeanMap.entrySet().iterator();
                    while (iterator.hasNext()) {
                        Map.Entry<String, Object> next = iterator.next();
                        Object serviceBean = next.getValue();
                        NettyRpcService nettyRpcService = serviceBean.getClass().getAnnotation(NettyRpcService.class);
                        String interfaceName = nettyRpcService.value().getName();
                        String version = nettyRpcService.version();
                        String serviceKey = CommonUtils.makeServiceKey(interfaceName, version);
                        registryConfig.setHost(IpUtils.getIp());
                        registryConfig.setPort(8888);
                        registryConfig.getServiceMap().put(serviceKey, serviceBean);
                    }
                }
            }
        };
        applicationContextAware.setApplicationContext(applicationContext);
        return registryConfig;
    }


}
