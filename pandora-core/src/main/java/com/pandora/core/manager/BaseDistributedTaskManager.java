package com.pandora.core.manager;

import lombok.Data;

@Data
public abstract class BaseDistributedTaskManager implements DistributedTaskManager {

    private StatusEnum status;

    public BaseDistributedTaskManager(){
        this.status=StatusEnum.INIT;
        this.prePare();
    }

    @Override
    public void start() {
        if(StatusEnum.RUNNING==status){
            this.execute();
        }
    }


    @Override
    public void stop() {
        if(StatusEnum.INIT==status||StatusEnum.RUNNING==status){
            this.status=StatusEnum.STOPPED;
        }
    }

    public enum StatusEnum{
        INIT,
        RUNNING,
        STOPPED
    }
}
