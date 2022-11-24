package com.pandora;

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
