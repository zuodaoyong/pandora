package com.pandora.core.manager.partition;

import com.pandora.common.ThreadPoolUtils;
import com.pandora.core.backend.MetaStoreBackend;
import com.pandora.core.manager.BaseDistributedTaskManager;
import com.pandora.core.manager.node.NodeManager;
import com.pandora.core.manager.partition.executor.PartitionReBalanceExecutor;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

@Component
@Data
public class PartitionManager extends BaseDistributedTaskManager {

    @Resource
    private MetaStoreBackend metaStoreBackend;

    @Resource
    private PartitionReBalanceExecutor partitionReBalanceExecutor;

    private List<Integer> holdingPartitions;

    private ThreadPoolExecutor threadPool;

    @Override
    public void prePare() {
        super.prePare();
        threadPool = ThreadPoolUtils.createThreadPool(NodeManager.class.getSimpleName(), 1, 1);
    }

    @Override
    public void execute() {
        //注册分区
        metaStoreBackend.registerPartitionInfo();
        threadPool.submit(partitionReBalanceExecutor);
    }

}
