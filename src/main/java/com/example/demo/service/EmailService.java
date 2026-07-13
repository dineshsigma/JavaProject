package com.example.demo.service;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;



@Service
public class EmailService {
	
	@Async
	public void sendEmail(String email) {
		try {
			Thread.sleep(5000);
		}catch(Exception e) {
			 e.printStackTrace();
		}
		
		System.out.println("Welcome Email Sent To : ");
		
	}

}
