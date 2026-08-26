package com.example.GmailSender.Service;

import com.example.GmailSender.model.GmailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;

@Service
public class GEmailSenderService {

    @Autowired
    JavaMailSender javaMailSender;

    public void sendMail(GmailSender gmailSender) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(gmailSender.getTo());
        message.setSubject(gmailSender.getSubject());
        message.setText(gmailSender.getMessage());

        javaMailSender.send(message);

    }
}
