package com.pandora.core.service.impl;

import com.pandora.annotation.NettyRpcService;
import com.pandora.core.service.HelloService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NettyRpcService(value = HelloService.class,version = "1.0")
public class HelloServiceImpl implements HelloService {


    @Override
    public void hello(String name) {
        log.info("hello "+name);
    }
}
