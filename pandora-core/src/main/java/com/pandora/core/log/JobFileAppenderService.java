package com.pandora.core.log;

import com.pandora.core.param.LogConfigProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;

@Component
public class JobFileAppenderService {

    @Resource
    private LogConfigProperties logConfigProperties;

    private String logBasePath;

    public void initLogPath(){
        String basePath = logConfigProperties.getBasePath();
        if(StringUtils.isEmpty(basePath)){
            return;
        }
        File basePathDir = new File(basePath);
        if(!basePathDir.exists()){
            basePathDir.mkdirs();
        }
        logBasePath = basePathDir.getPath();
    }

    public String getLogBasePath(){
        if(this.logBasePath==null){
            initLogPath();
        }
        return logBasePath;
    }


}
