package com.add.calendar.feignClient;

import org.springframework.cloud.openfeign.FeignClient;

import com.add.calendar.feignClient.config.FeignClientConfig;

@FeignClient(value = "notification", configuration = FeignClientConfig.class)
public interface NotificationRestService {

}
