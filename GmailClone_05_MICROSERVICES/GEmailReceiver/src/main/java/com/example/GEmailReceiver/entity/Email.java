package com.example.GEmailReceiver.entity;


import lombok.Data;

import java.util.Date;

@Data
public class Email {

    private String from;
    private String subject;
    private Date revecivedDate;
    private String content;
}
