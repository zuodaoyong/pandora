package com.pandora.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface NettyRpcService {

    /**
     * 服务类
     *
     * @return
     */
    Class<?> value();

    /**
     * 版本
     *
     * @return
     */
    String version() default "";
}
