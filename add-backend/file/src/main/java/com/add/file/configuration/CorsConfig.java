package com.add.file.configuration;

import org.springframework.context.annotation.Configuration;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
        .allowedOrigins("*")
        .allowCredentials(false)
        .maxAge(3600)
        .allowedHeaders("*")
        .exposedHeaders("*")
        .allowedMethods("POST", "GET", "DELETE", "PUT", "PATCH", "OPTIONS");
  }

}
