package com.add.course.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class Version {

  @PostConstruct
  public void javaVersion() {

    log.info("JAVA version used in this service is {}", System.getProperty("java.version"));
  }

}
