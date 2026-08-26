package com.example.GEmailReceiver.controller;

import com.example.GEmailReceiver.entity.Email;
import com.example.GEmailReceiver.entity.EmailRequest;
import com.example.GEmailReceiver.service.GemailReceiverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.restclient.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
public class GemailReceiverController {

    @Autowired
    private GemailReceiverService gemailReceiverService;

    private final RestTemplate restTemplate;

    public GemailReceiverController(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @GetMapping("/")
    public String inbox(Model model) {

        List<Email> emailList = gemailReceiverService.getEmails();

        model.addAttribute("emailslist", emailList);

        return "inbox";
    }

    @PostMapping("/sendmail")
    public String sendMail(
            @ModelAttribute EmailRequest emailRequest,
            Model model) {

        try {

            // EMAIL SENDER MICROSERVICE
            String url = "http://localhost:8080/sendmail";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<EmailRequest> httpEntity =
                    new HttpEntity<>(emailRequest, headers);

            ResponseEntity<String> response =
                    restTemplate.postForEntity(
                            url,
                            httpEntity,
                            String.class
                    );

            if (response.getStatusCode() == HttpStatus.OK) {

                model.addAttribute(
                        "success",
                        "Email sent successfully"
                );

            } else {

                model.addAttribute(
                        "failed",
                        "Failed to send email"
                );
            }

        } catch (Exception e) {

            e.printStackTrace();

            model.addAttribute(
                    "ErrorMessage",
                    "Process failed: " + e.getMessage()
            );
        }

        // IMPORTANT:
        // inbox.html needs emailslist
        List<Email> emailList = gemailReceiverService.getEmails();
        model.addAttribute("emailslist", emailList);

        return "inbox";
    }
}