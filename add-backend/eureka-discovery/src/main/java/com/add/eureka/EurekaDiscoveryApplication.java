package com.add.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

import com.add.eureka.configuration.LoggingListener;

@SpringBootApplication
@EnableEurekaServer
public class EurekaDiscoveryApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(EurekaDiscoveryApplication.class);
        application.addListeners(new LoggingListener());
        application.run(args);
    }

}
