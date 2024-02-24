package com.add.calendar.startup;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.add.calendar.services.EventService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OnStartup {

  private final EventService eventService;

  @PostConstruct
  public void scheduelEvents() {

    eventService.scheduelEventOnReboot();
  }

}
