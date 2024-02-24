package com.add.notification.feignClient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.add.notification.feignClient.config.FeignClientConfig;

// @FeignClient(name = "auth", url = "http://auth:8000", configuration = FeignClientConfig.class)
@FeignClient(value = "auth", configuration = FeignClientConfig.class)
public interface IAuthRest {

  @GetMapping(value = "/api/auth/users/active/id")
  List<String> getActiveUserIds();

  @GetMapping(value = "/api/auth/users/group/{groupName}")
  List<String> getUserIdsByGroupName(@PathVariable String groupName);

}
