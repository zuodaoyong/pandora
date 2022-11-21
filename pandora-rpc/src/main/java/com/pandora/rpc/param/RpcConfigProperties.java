package com.pandora.rpc.param;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "rpc")
@Data
public class RpcConfigProperties {

    /**
     * rpc服务地址
     */
    private String serverAddress;

    /**
     * rpc服务端口
     */
    private String serverPort;

    private Registry registry = new Registry();


    @Data
    public static class Registry {

        private Zookeeper zookeeper = new Zookeeper();

        private Store store = Store.Zookeeper;

        @Data
        public static class Zookeeper {

            private String address;

            private String port;

        }

        public enum Store {
            Zookeeper,

            Mysql
        }
    }


}
