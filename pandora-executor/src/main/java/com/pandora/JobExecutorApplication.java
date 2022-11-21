package com.pandora;

import com.pandora.rpc.param.RpcConfigProperties;
import com.pandora.rpc.registry.model.RegistryConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@MapperScan("com.pandora.mysql.Mapper")
public class JobExecutorApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(JobExecutorApplication.class, args);


    }
}
