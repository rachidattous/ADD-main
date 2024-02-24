package com.add.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import org.springframework.scheduling.annotation.EnableAsync;

import com.add.auth.configuration.LoggingListener;

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
@EnableFeignClients
@EnableAsync
public class AuthServiceApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(AuthServiceApplication.class);
		application.addListeners(new LoggingListener());
		application.run(args);
	}

}
