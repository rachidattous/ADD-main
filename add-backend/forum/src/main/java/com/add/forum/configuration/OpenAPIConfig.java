package com.add.forum.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
@OpenAPIDefinition(info = @Info(title = "forum", version = "1.0", description = "ADD forum Service"))
public class OpenAPIConfig {

  private static final String SECURITY_SCHEME_NAME = "bearerAuth";

  private static final String SECURITY_SCHEME = "bearer";

  private static final String SECURITY_BEARER_FORMAT = "bearer";

  @Bean
  public OpenAPI openAPI() {

    return new OpenAPI()
        .addSecurityItem(securityRequirement())
        .components(components());

  }

  private SecurityRequirement securityRequirement() {
    return new SecurityRequirement()
        .addList(SECURITY_SCHEME_NAME);

  }

  private Components components() {
    return new Components()
        .addSecuritySchemes(SECURITY_SCHEME_NAME, securityScheme());

  }

  private SecurityScheme securityScheme() {

    return new SecurityScheme()
        .name(SECURITY_SCHEME_NAME)
        .type(SecurityScheme.Type.HTTP)
        .scheme(SECURITY_SCHEME)
        .bearerFormat(SECURITY_BEARER_FORMAT);

  }

}
