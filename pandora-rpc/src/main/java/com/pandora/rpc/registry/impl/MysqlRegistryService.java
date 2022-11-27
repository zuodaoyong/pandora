package com.pandora.rpc.registry.impl;

import com.pandora.mysql.Mapper.NodeInfoMapper;
import com.pandora.mysql.Mapper.RegistryInfoMapper;
import com.pandora.mysql.model.RegistryInfo;
import com.pandora.rpc.condition.MysqlCondition;
import com.pandora.rpc.param.RpcConfigProperties;
import com.pandora.rpc.protocol.RpcProtocol;
import com.pandora.rpc.registry.RegistryService;
import com.pandora.utils.CommonUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

/**
 * mysql注册中心
 */
@Component
@Conditional(MysqlCondition.class)
public class MysqlRegistryService implements RegistryService {

    @Resource
    private RegistryInfoMapper registryInfoMapper;

    @Resource
    private NodeInfoMapper nodeInfoMapper;



    @Resource
    private RpcConfigProperties rpcConfigProperties;

    @Transactional(rollbackFor=Exception.class)
    @Override
    public void registerService(RpcProtocol rpcProtocol) {
        //清楚历史服务
        registryInfoMapper.truncate();
        Date now =new Date();
        Map<String, Object> serviceMap = rpcProtocol.getServiceMap();
        if(MapUtils.isNotEmpty(serviceMap)){
            Iterator<Map.Entry<String, Object>> iterator = serviceMap.entrySet().iterator();
            while (iterator.hasNext()){
                Map.Entry<String, Object> next = iterator.next();
                String serviceKey = next.getKey();
                Pair<String, String> serviceVersionPair = CommonUtils.serviceVersionPair(serviceKey);
                RegistryInfo registryInfo=new RegistryInfo();
                registryInfo.setNodeName(rpcProtocol.getHost());
                registryInfo.setPort(rpcProtocol.getPort());
                registryInfo.setGmtCreate(now);
                registryInfo.setGmtModify(now);
                registryInfo.setVersion(serviceVersionPair.getValue());
                registryInfo.setServiceName(serviceVersionPair.getKey());
                registryInfoMapper.insert(registryInfo);
            }
        }
    }






}
