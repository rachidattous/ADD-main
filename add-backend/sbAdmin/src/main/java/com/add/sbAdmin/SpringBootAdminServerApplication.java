package com.add.sbAdmin;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import com.add.sbAdmin.configuration.LoggingListener;

@EnableAdminServer
@EnableEurekaClient
@EnableAutoConfiguration
@SpringBootApplication
public class SpringBootAdminServerApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(SpringBootAdminServerApplication.class);
		application.addListeners(new LoggingListener());
		application.run(args);
	}

}
