package com.add.calendar.controllers;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/calendar/test")
@RequiredArgsConstructor
public class TestController {

  @GetMapping("/hi")
  public String getHi() {
    return "Hi";
  }

}
