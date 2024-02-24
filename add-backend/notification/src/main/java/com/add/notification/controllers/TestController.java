package com.add.notification.controllers;

import com.add.notification.services.RestService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notifications/test")
public class TestController {

    private final RestService restService;

    @GetMapping("/users")
    public List<String> getUsers() {
        return restService.getActiveUserIds();
    }

}
