package com.pandora.core.executor;

import com.pandora.core.log.JobFileAppenderService;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;

@Slf4j
public class JobExecutor {

    private String adminAddresses;

    private String accessToken;

    private String appname;

    private String address;

    private String ip;

    private int port;

    private String logPath;

    private int logRetentionDays;

    @Resource
    private JobFileAppenderService jobFileAppenderService;

    public void start(){
        //初始化日志路径
        jobFileAppenderService.initLogPath();


    }
}
