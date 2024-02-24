package com.add.auth.configuration;

import org.springframework.context.annotation.Configuration;
import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.OAuth2Constants;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

@Configuration
public class KeycloakConfiguration {

  @Value("${keycloak.credentials.secret}")
  private String secretKey;

  @Value("${keycloak.resource}")
  private String clientId;

  @Value("${keycloak.auth-server-url}")
  private String authUrl;

  @Value("${keycloak.realm}")
  private String realm;

  @Bean
  public Keycloak keycloak() {

    return KeycloakBuilder.builder()
        .realm(realm)
        .clientId(clientId)
        .clientSecret(secretKey)
        .serverUrl(authUrl)
        .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
        .build();

  }

  @Bean
  public KeycloakConfigResolver keycloakConfigResolver() {
    return new KeycloakSpringBootConfigResolver();
  }
}
