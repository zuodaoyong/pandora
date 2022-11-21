package com.pandora.rpc.registry.model;

import lombok.Data;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@ToString
@Data
public class RegistryConfig {

    private String host;

    private int port;

    private Map<String,Object> serviceMap=new HashMap<>();

}
