package com.add.communication.configuration;

import java.text.DecimalFormat;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;

import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.gson.Gson;

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
  public Gson getGSON() {
    return new Gson();

  }

}
