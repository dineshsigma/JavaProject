package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.Worker;

import com.example.demo.service.WrokerService;


@RestController
@RequestMapping("/api/worker")
public class WorkerController {
	
	private final WrokerService wrokerService;
	
	public WorkerController(WrokerService wrokerService) {
		this.wrokerService = wrokerService;
		
	}
	
	@PostMapping
	public String registerWorker(@RequestBody Worker work) {
		return wrokerService.registerUser(work);
	}

}
