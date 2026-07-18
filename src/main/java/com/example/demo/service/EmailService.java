package com.example.demo.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@Service
public class EmailService {
	
	private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

	@Async
	public void sendEmail(String email) {
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setTo(email);
			message.setSubject("Registration Successful");
			message.setText("""
					Dear User,

					Your registration was completed successfully.

					Welcome to our platform.

					Regards,
					Admin Team
					""");
			 mailSender.send(message);
			 System.out.println("Welcome Email Sent To : ");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
