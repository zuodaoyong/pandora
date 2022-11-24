package com.pandora.common;

public class Constants {

    public static final String SERVICE_CONCAT_TOKEN = "#";


    public static class Zookeeper{
       public static final int ZK_SESSION_TIMEOUT = 5000;
       public static final int ZK_CONNECTION_TIMEOUT = 5000;
       public static final String ZK_REGISTRY_PATH = "/registry";
       public static final String ZK_DATA_PATH = ZK_REGISTRY_PATH + "/data";
       public static final String ZK_NAMESPACE = "netty-rpc";
    }
}
