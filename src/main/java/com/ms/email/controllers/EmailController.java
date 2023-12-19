package com.ms.email.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.ms.email.dtos.EmailRecordDto;
import com.ms.email.services.EmailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/send-email")
    public ResponseEntity<Object> sendMail(@RequestBody EmailRecordDto emailRecordDto) {
        return emailService.publishMessage(emailRecordDto);
    }

}
