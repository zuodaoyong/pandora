package com.pandora.core.biz;

import com.pandora.core.biz.model.HandleCallbackParam;
import com.pandora.core.biz.model.RegistryParam;
import com.pandora.core.biz.model.ReturnT;

import java.util.List;

public interface AdminBiz {

    /**
     * callback
     *
     * @param callbackParamList
     * @return
     */
    ReturnT<String> callback(List<HandleCallbackParam> callbackParamList);

    /**
     * registry
     *
     * @param registryParam
     * @return
     */
    ReturnT<String> registry(RegistryParam registryParam);

    /**
     * remove
     *
     * @param registryParam
     * @return
     */
    ReturnT<String> registryRemove(RegistryParam registryParam);


}
