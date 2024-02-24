package com.add.communication.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping
@RequiredArgsConstructor
public class TestWSController {

  @GetMapping("/")
  public String getHi() {
    log.info("hi");
    return "index";
  }

  @GetMapping("/conf")
  public String conf() {
    log.info("hi");
    return "conf";
  }

  @GetMapping("/screenshare")
  public String screenshare() {
    log.info("hi");
    return "screenshare";
  }

}
