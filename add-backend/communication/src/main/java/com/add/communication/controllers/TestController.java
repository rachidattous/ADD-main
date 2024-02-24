package com.add.communication.controllers;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/chat/test")
@RequiredArgsConstructor
public class TestController {

  @GetMapping("/hi")

  public String getHi() {
    log.info("hi");
    return "Hi";
  }

}
