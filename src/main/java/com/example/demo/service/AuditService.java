package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Async;

@Service
public class AuditService {

	@Async
	public void createAudit() {
		System.out.println("Audit logs Addedd");
	}

}
