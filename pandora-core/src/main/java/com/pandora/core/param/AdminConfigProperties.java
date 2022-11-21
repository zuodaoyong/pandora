package com.pandora.core.param;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "admin")
@Component
@Data
public class AdminConfigProperties {

    private String addressUrl;

    private String accessToken;

    private int timeout = 3;

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
