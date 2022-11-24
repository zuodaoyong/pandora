package com.pandora.utils;


import com.pandora.common.Constants;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

public class CommonUtils {

    /**
     * seviceName和version组装成key
     *
     * @param interfaceName
     * @param version
     * @return
     */
    public static String makeServiceKey(String interfaceName, String version) {
        String serviceKey = interfaceName;
        if (version != null && version.trim().length() > 0) {
            serviceKey += Constants.SERVICE_CONCAT_TOKEN.concat(version);
        }
        return serviceKey;
    }

    /**
     * key转换为serviceName和version
     *
     * @param serviceKey
     * @return
     */
    public static Pair<String, String> serviceVersionPair(String serviceKey) {
        String[] split = serviceKey.split(Constants.SERVICE_CONCAT_TOKEN);
        return new ImmutablePair<>(split[0], split[1]);
    }


}
