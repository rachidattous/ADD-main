package com.add.auth.feignClient;

import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.add.auth.dto.MailDTO;
import com.add.auth.feignClient.config.FeignClientConfig;

@FeignClient(value = "notification", configuration = FeignClientConfig.class)
public interface NotificationRestService {

  @PostMapping(value = "/api/notifications/email")
  public void sendMail(@RequestBody MailDTO mail);

}
