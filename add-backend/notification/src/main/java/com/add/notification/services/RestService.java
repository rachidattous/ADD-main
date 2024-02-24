package com.add.notification.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.add.notification.feignClient.IAuthRest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class RestService {

  private final IAuthRest iAuthRest;

  public List<String> getActiveUserIds() {
    try {
      return iAuthRest.getActiveUserIds();
    } catch (Exception e) {
      log.error(e.getMessage());
      return new ArrayList<>();
    }
  }

  public List<String> getUserIdsByGroupName(String groupName) {
    try {
      return iAuthRest.getUserIdsByGroupName(groupName);
    } catch (Exception e) {
      log.error(e.getMessage());
      return new ArrayList<>();
    }
  }

}
