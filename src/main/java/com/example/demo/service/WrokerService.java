package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Worker;
import com.example.demo.repository.WorkerRepository;
import com.example.demo.service.EmailService;

@Service
public class WrokerService {

	private final EmailService emailService;
	private final WorkerRepository workerRepository;

	public WrokerService(EmailService emailService, WorkerRepository workerRepository) {
		this.emailService = emailService;
		this.workerRepository = workerRepository;

	}

	public String registerUser(Worker worker) {

		workerRepository.save(worker);

		emailService.sendEmail(worker.getEmail());

		return "User Registered Successfully";
	}

}
