package com.add.notification.controllers;

import com.add.notification.dto.MailFileDTO;
import com.add.notification.dto.MailDTO;
import com.add.notification.services.EmailSenderService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.MediaType;

import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notifications/email")
public class EmailController {

    private final EmailSenderService emailSenderService;

    @PutMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public void sendMailWithFiles(@ModelAttribute MailFileDTO mail) {
        emailSenderService.sendMail(mail);
    }

    @PostMapping
    public void sendMail(@RequestBody MailDTO mail) {
        emailSenderService.sendMail(mail);
    }

}
