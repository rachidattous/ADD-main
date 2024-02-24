package com.add.file;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import org.springframework.scheduling.annotation.EnableAsync;

import com.add.file.configuration.LoggingListener;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@EnableAsync
public class FileApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(FileApplication.class);
		application.addListeners(new LoggingListener());
		application.run(args);
	}

}
