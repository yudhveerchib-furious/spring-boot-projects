package com.example.GEmailReceiver.entity;


import lombok.Data;

@Data
public class EmailRequest {
   private String to;
   private String subject;
   private String message;
}
