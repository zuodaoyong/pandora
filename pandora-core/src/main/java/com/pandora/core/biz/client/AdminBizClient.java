package com.pandora.core.biz.client;

import com.pandora.core.biz.AdminBiz;
import com.pandora.core.biz.model.HandleCallbackParam;
import com.pandora.core.biz.model.RegistryParam;
import com.pandora.core.biz.model.ReturnT;
import com.pandora.core.param.AdminConfigProperties;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class AdminBizClient implements AdminBiz {

    @Resource
    private AdminConfigProperties adminConfigProperties;

    @Override
    public ReturnT<String> callback(List<HandleCallbackParam> callbackParamList) {
        return null;
    }

    @Override
    public ReturnT<String> registry(RegistryParam registryParam) {
        return null;
    }

    @Override
    public ReturnT<String> registryRemove(RegistryParam registryParam) {
        return null;
    }
}
