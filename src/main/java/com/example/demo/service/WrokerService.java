package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Worker;
import com.example.demo.repository.WorkerRepository;
import com.example.demo.service.EmailService;
import org.springframework.context.ApplicationEventPublisher;
import com.example.demo.listner.UserRegisteredEvent;

@Service
public class WrokerService {

	private final EmailService emailService;
	private final WorkerRepository workerRepository;
	 private final ApplicationEventPublisher publisher;

	public WrokerService(EmailService emailService, WorkerRepository workerRepository,ApplicationEventPublisher publisher) {
		this.emailService = emailService;
		this.workerRepository = workerRepository;
		this.publisher = publisher;

	}

	public String registerUser(Worker worker) {

		workerRepository.save(worker); // after saving  Add publish Event listner

        //emailService.sendEmail(worker.getEmail()); //this one I am using @async processing
		
		publisher.publishEvent(new UserRegisteredEvent(worker.getEmail()));  // this code is related to @EventListner
		
		return "User Registered Successfully";
	}

}
