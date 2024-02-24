package com.add.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import org.springframework.scheduling.annotation.EnableAsync;

import com.add.search.configuration.LoggingListener;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@EnableAsync
public class SearchApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(SearchApplication.class);
		application.addListeners(new LoggingListener());
		application.run(args);
	}

}
