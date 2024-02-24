package com.add.course.feignClient.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.RefreshableKeycloakSecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

@Slf4j
@Configuration
public class FeignClientConfig {

     @Bean
     protected RequestInterceptor keycloakRequestInterceptor(KeycloakSecurityContext keycloakSecurityContext) {
          return new KeycloakRequestInterceptor(keycloakSecurityContext);
     }

     @RequiredArgsConstructor
     static class KeycloakRequestInterceptor implements RequestInterceptor {

          private final KeycloakSecurityContext keycloakSecurityContext;

          private static final String BEARER_PREFIX = "Bearer ";

          @Override
          public void apply(RequestTemplate template) {
//               if (whitelist(template.url())) {
//                    return;
//               }
               ensureTokenIsStillValid();
               template.header(HttpHeaders.AUTHORIZATION, BEARER_PREFIX + getKeycloakAccessToken());

          }

          private void ensureTokenIsStillValid() {
               if (keycloakSecurityContext instanceof RefreshableKeycloakSecurityContext) {
                    log.warn("Refresh token !");
                    RefreshableKeycloakSecurityContext.class.cast(keycloakSecurityContext).refreshExpiredToken(true);
               }
          }

          private boolean whitelist(String url) {

               return Arrays.asList("")
                       .stream()
                       .anyMatch(url::contains);

          }

          private String getKeycloakAccessToken() {

               return keycloakSecurityContext.getTokenString();
          }

     }

}
