package com.example.finalproject_phase2.controller;

import com.example.finalproject_phase2.entity.EmailRequest;
import com.example.finalproject_phase2.service.impl.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("email")
public class MailController {
    private final MailService mailService;
@Autowired
    public MailController(MailService mailService) {
        this.mailService = mailService;
    }


    @PostMapping("/send")
    public String sendEmail(@RequestBody EmailRequest emailRequest) {
        mailService.sendEmail(emailRequest.getTo(), emailRequest.getSubject(), emailRequest.getText());
        return "Email sent successfully!";
    }
}
