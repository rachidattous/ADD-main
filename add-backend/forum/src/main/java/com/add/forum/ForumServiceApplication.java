package com.add.forum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import org.springframework.scheduling.annotation.EnableAsync;

import com.add.forum.configuration.LoggingListener;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@EnableAsync
public class ForumServiceApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(ForumServiceApplication.class);
		application.addListeners(new LoggingListener());
		application.run(args);
	}

}
