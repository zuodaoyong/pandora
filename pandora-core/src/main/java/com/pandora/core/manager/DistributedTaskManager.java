package com.pandora.core.manager;

public interface DistributedTaskManager {

    void start();

    void prePare();

    void execute();

    void stop();
}
