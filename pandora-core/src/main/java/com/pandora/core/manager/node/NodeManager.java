package com.pandora.core.manager.node;

import com.pandora.core.backend.MetaStoreBackend;
import com.pandora.core.manager.BaseDistributedTaskManager;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;

@Component
public class NodeManager extends BaseDistributedTaskManager {


    @Resource
    private MetaStoreBackend metaStoreBackend;

    @Override
    public void prePare() {
        this.setStatus(StatusEnum.RUNNING);
    }

    @Override
    public void execute() {
        //注册节点
        metaStoreBackend.registerNodeInfo();

        //心跳管理器


    }

}
