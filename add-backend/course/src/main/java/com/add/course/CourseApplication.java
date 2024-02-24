package com.add.course;

import com.add.course.configuration.LoggingListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@EnableAsync
public class CourseApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(CourseApplication.class);
		application.addListeners(new LoggingListener());
		application.run(args);
	}

}
