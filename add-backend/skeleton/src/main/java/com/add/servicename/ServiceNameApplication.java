package com.add.servicename;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import org.springframework.scheduling.annotation.EnableAsync;

import com.add.servicename.configuration.LoggingListener;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@EnableAsync
public class ServiceNameApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(ServiceNameApplication.class);
		application.addListeners(new LoggingListener());
		application.run(args);
	}

}
