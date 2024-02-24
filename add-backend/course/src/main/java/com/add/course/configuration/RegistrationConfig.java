package com.add.course.configuration;

import com.add.course.exception.ApiException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.kubernetes.discovery.KubernetesDiscoveryProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RegistrationConfig {

  @Value("${eureka.client.enabled}")
  private boolean isEurekaEnabled;

  @Value("${spring.cloud.kubernetes.enabled}")
  private boolean isKuKubernetesEnabled;

  @Bean
  public KubernetesDiscoveryProperties kubernetesDiscoveryProperties() {

    if (isEurekaEnabled == true && isKuKubernetesEnabled == true) {
      throw new ApiException("Eureka & Kubernetes are both enabled one of them should be disabled");
    }

    return new KubernetesDiscoveryProperties();
  }

}
