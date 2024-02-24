package com.add.file.feignClient.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

          @Override
          public void apply(RequestTemplate template) {
               ensureTokenIsStillValid();
               template.header(HttpHeaders.AUTHORIZATION, "Bearer " + keycloakSecurityContext.getTokenString());
          }

          private void ensureTokenIsStillValid() {
               if (keycloakSecurityContext instanceof RefreshableKeycloakSecurityContext) {
                    log.warn("Refresh token !");
                    RefreshableKeycloakSecurityContext.class.cast(keycloakSecurityContext).refreshExpiredToken(true);
               }
          }

     }

}