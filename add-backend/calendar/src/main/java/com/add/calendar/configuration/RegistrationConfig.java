package com.add.calendar.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.add.calendar.exception.config.ApiException;

import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.kubernetes.discovery.KubernetesDiscoveryProperties;

@Configuration
public class RegistrationConfig {

  @Value("${eureka.client.enabled}")
  private boolean isEurekaEnabled;

  @Value("${spring.cloud.kubernetes.enabled}")
  private boolean isKuKubernetesEnabled;

  @Bean
  @Primary
  public KubernetesDiscoveryProperties kubernetesDiscoveryProperties() {

    if (isEurekaEnabled && isKuKubernetesEnabled) {
      throw new ApiException("Eureka & Kubernetes are both enabled one of them should be disabled");
    }

    return new KubernetesDiscoveryProperties();
  }

}
