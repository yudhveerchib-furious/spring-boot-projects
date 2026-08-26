package com.example.GmailSender.controller;

import com.example.GmailSender.Service.GEmailSenderService;
import com.example.GmailSender.model.GmailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class GEmailSenderController {

    @Autowired
    GEmailSenderService gEmailSenderService;

    @GetMapping("/")
    public String form() {
        return "GEmailSenderForm";
    }

    @PostMapping("/sendmail")
    @ResponseBody
    public String sendEmail(@RequestBody GmailSender gmailSender) {

        gEmailSenderService.sendMail(gmailSender);

        return "sent";
    }
}