package com.add.servicename.configuration;

import java.text.DecimalFormat;
import java.util.Random;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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

    // PropertyMap<SOURCE, DESTINATION> mapping = new PropertyMap<SOURCE,
    // DESTINATION>() {
    // protected void configure() {
    // map().set(sourceAttribute)(source.get(destinationAttribute));

    // }
    // };

    // modelMapper.addMappings(mapping);
    return modelMapper;
  }

  @Bean
  public Random getRandom() {
    return new Random();
  }

}
