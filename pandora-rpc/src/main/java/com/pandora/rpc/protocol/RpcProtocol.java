package com.pandora.rpc.protocol;

import lombok.Data;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@ToString
@Data
public class RpcProtocol {

    private String host;

    private int port;

    private Map<String,Object> serviceMap=new HashMap<>();

}
