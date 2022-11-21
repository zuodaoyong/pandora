package com.pandora.rpc.registry;

import com.pandora.annotation.NettyRpcService;
import com.pandora.rpc.registry.condition.MysqlCondition;
import com.pandora.rpc.registry.condition.ZookeeperCondition;
import com.pandora.rpc.registry.impl.MysqlRegistryService;
import com.pandora.rpc.registry.impl.ZookeeperRegistryService;
import com.pandora.rpc.registry.model.RegistryConfig;
import com.pandora.utils.CommonUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.*;

import java.net.Inet4Address;
import java.util.Iterator;
import java.util.Map;

@Configuration
public class RegistryAutoConfiguration {

    /**
     * mysql注册中心
     * @return
     */
    @Bean
    @Conditional(MysqlCondition.class)
    public MysqlRegistryService mysqlRegistryService(){
        return new MysqlRegistryService();
    }

    /**
     * zk注册中心
     * @return
     */
    @Bean
    @Conditional(ZookeeperCondition.class)
    public ZookeeperRegistryService zookeeperRegistryService(){
        return new ZookeeperRegistryService();
    }


    /**
     * 注册服务
     * @return
     */
    @Bean
    public RegistryConfig registryConfig(){
        final RegistryConfig registryConfig =new RegistryConfig();
        try{
            Inet4Address address= (Inet4Address) Inet4Address.getLocalHost();
            String hostAddress = address.getHostAddress();
            registryConfig.setHost(hostAddress);
        }catch (Exception e){
            e.printStackTrace();
        }

        new ApplicationContextAware(){
            @Override
            public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
                Map<String, Object> serviceBeanMap = applicationContext.getBeansWithAnnotation(NettyRpcService.class);
                if (MapUtils.isNotEmpty(serviceBeanMap)) {
                    Iterator<Map.Entry<String, Object>> iterator = serviceBeanMap.entrySet().iterator();
                    while (iterator.hasNext()){
                        Map.Entry<String, Object> next = iterator.next();
                        Object serviceBean = next.getValue();
                        NettyRpcService nettyRpcService = serviceBean.getClass().getAnnotation(NettyRpcService.class);
                        String interfaceName = nettyRpcService.value().getName();
                        String version = nettyRpcService.version();
                        String serviceKey = CommonUtils.makeServiceKey(interfaceName, version);
                        registryConfig.getServiceMap().put(serviceKey, serviceBean);
                    }
                }
            }
        };
        System.out.println(registryConfig);
        return registryConfig;
    }


}
