package com.example.demo.listner;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.example.demo.service.AuditService;
import com.example.demo.service.EmailService;
import org.springframework.scheduling.annotation.Async;

@Component
public class UserRegistrationListener {

	private final EmailService emailService;
	private final AuditService auditService;

	public UserRegistrationListener(EmailService emailService, AuditService auditService) {

		this.emailService = emailService;
		this.auditService = auditService;
	}

	@EventListener
	@Async
	public void handleUserRegistration(UserRegisteredEvent event) {

		emailService.sendEmail(event.getEmail());

		auditService.createAudit();
	}

}
