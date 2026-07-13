package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.CustomerRegistrationRequest;
import com.example.demo.entity.ApiResponse;
import com.example.demo.service.CustomerService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

	private final CustomerService customerService;
	
	@PostMapping("/api/register")
	public ResponseEntity<ApiResponse<String>> register(@Valid @RequestBody CustomerRegistrationRequest request) {

		String response = customerService.registerCustomer(request);

		return ResponseEntity.ok(new ApiResponse<>(201, "Customer Registered Successfully", response));

	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping("/api/employees")
	public ResponseEntity<?> getEmployees() {
		return ResponseEntity.ok("Success");
	}

}
