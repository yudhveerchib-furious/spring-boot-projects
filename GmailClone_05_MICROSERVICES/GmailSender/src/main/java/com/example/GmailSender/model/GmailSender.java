package com.example.GmailSender.model;

import lombok.Data;


@Data
public class GmailSender {
    private String to;
    private String subject;
    private String message;
}
