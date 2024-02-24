package com.add.communication.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Profile("dev")
@Configuration
public class CorsConfig implements WebMvcConfigurer {

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
        .allowedOrigins("*")
        .allowCredentials(false)
        .maxAge(3600)
        .allowedHeaders("Accept", "Content-Type", "Origin",
            "Authorization", "X-Auth-Token", "responseType")
        .exposedHeaders("X-Auth-Token", "Authorization", "responseType")
        .allowedMethods("POST", "GET", "DELETE", "PUT", "PATCH", "OPTIONS");
  }

}
