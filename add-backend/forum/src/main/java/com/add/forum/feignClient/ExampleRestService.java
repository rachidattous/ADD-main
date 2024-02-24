package com.add.forum.feignClient;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.add.forum.feignClient.config.FeignClientConfig;

@FeignClient(name = "SERVICE", url = "http://add.com:8080/SERVICE", configuration = FeignClientConfig.class)
public interface ExampleRestService {

  @GetMapping(value = "/api/test/hi")
  public String getHi();

}

// // in a container
// @FeignClient(value = "SERVICE", configuration = FeignClientConfig.class)
// public interface ExampleRestService {

// @GetMapping(value = "/api/test/hi")
// public String getHi();

// }
