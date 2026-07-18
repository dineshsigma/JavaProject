package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.service.OtpService;

import lombok.RequiredArgsConstructor;

import com.example.demo.dto.GenerateOtpRequest;
import com.example.demo.entity.ApiResponse;

@RestController
@RequestMapping("/api/otp")
@RequiredArgsConstructor
public class OtpController {

	private final OtpService otpService;

	@PostMapping
	public ResponseEntity<ApiResponse> generateOtp(@RequestBody GenerateOtpRequest request) {

		System.out.println("request" + request.getEmail());

		ApiResponse response = otpService.generateOtp(request);

		return ResponseEntity.ok(response);

	}

}
