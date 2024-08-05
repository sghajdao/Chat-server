package com.chat.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chat.dto.VerifyEmailRequest;
import com.chat.services.MailSenderService;

@RestController
@RequestMapping("/api/mail")
public class MailSenderController {

    @Autowired
    private MailSenderService mailSenderService;

    @PostMapping("/verify")
    private ResponseEntity<Boolean> sendNumberToEmail(@RequestBody VerifyEmailRequest request) {
        return new ResponseEntity<>(mailSenderService.sendVerificationNumber(request), HttpStatus.OK);
    }
}
