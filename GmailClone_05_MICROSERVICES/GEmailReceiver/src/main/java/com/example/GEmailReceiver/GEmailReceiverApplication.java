package com.example.GEmailReceiver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GEmailReceiverApplication {

	public static void main(String[] args) {
		SpringApplication.run(GEmailReceiverApplication.class, args);
	}

}
