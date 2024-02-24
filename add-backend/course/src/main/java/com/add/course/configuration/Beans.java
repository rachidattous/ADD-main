package com.add.course.configuration;

import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.util.Random;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Beans {

  @Bean
  public DecimalFormat getDecimalFormat() {
    return new DecimalFormat("0.00");
  }

  @Bean
  public ModelMapper modelMapper() {
    ModelMapper modelMapper = new ModelMapper();
    modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

    return modelMapper;
  }

  @Bean
  public Random getRandom() {
    return new SecureRandom();
  }

  @Bean
  public RestTemplate getRestTemplate() {
    HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
    clientHttpRequestFactory.setConnectTimeout(10000);
    return new RestTemplate(clientHttpRequestFactory);
  }

}
