package com.add.notification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import org.springframework.scheduling.annotation.EnableAsync;
// import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import com.add.notification.configuration.LoggingListener;

@SpringBootApplication
@EnableEurekaClient
// @EnableDiscoveryClient
@EnableFeignClients
@EnableAsync
public class NotificationApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(NotificationApplication.class);
		application.addListeners(new LoggingListener());
		application.run(args);
	}

}
