package com.pandora.rpc.server.codec;

import lombok.Data;

import java.io.Serializable;

@Data
public class RpcResponse implements Serializable {

    private String requestId;

    private String error;

    private Object result;
}
