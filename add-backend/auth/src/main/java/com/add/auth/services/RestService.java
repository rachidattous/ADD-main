package com.add.auth.services;

import org.springframework.stereotype.Service;

import com.add.auth.dto.MailDTO;
import com.add.auth.feignClient.NotificationRestService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestService {

  private final NotificationRestService notificationRestService;

  public void sendEmail(MailDTO mail) {
    try {
      notificationRestService.sendMail(mail);

    } catch (Exception e) {
      log.error(e.getMessage());
    }
  }

}
