package com.pandora.core.manager.partition;

import com.pandora.core.backend.MetaStoreBackend;
import com.pandora.core.manager.BaseDistributedTaskManager;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class PartitionManager extends BaseDistributedTaskManager {

    @Resource
    private MetaStoreBackend metaStoreBackend;

    @Override
    public void prePare() {
        super.prePare();
    }

    @Override
    public void execute() {
        //注册分区
        metaStoreBackend.registerPartitionInfo();



    }


}
