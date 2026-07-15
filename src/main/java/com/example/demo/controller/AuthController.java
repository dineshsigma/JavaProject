package com.example.demo.controller;

import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.config.JwtUtil;
import com.example.demo.dto.CustomerRegistrationRequest;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.LoginResponse;
import com.example.demo.dto.PersonalDetailsDTO;
import com.example.demo.entity.ApiResponse;
import com.example.demo.entity.Customer;
import com.example.demo.entity.PersonalDetails;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.PersonalDetailsRepository;
import com.example.demo.repository.RoleRepository;
import jakarta.validation.Valid;


import com.example.commons.utlity.CommonConstants;
import com.example.commons.utlity.RandomGenerator;
import com.example.demo.entity.Role;


@RestController

@RequestMapping("/api/auth")
public class AuthController {

	private final JwtUtil jwtUtil;
	private final PasswordEncoder passwordEncoder;
	private final CustomerRepository customerRepository;
	private final PersonalDetailsRepository personalDetailsRepository;
	private final RoleRepository roleRepository;
	


	public AuthController(JwtUtil jwtUtil, PasswordEncoder passwordEncoder, CustomerRepository customerRepository,
			PersonalDetailsRepository personalDetailsRepository ,RoleRepository roleRepository

			) {
		this.jwtUtil = jwtUtil;
		this.passwordEncoder = passwordEncoder;
		this.customerRepository = customerRepository;
		this.personalDetailsRepository = personalDetailsRepository;
		this.roleRepository = roleRepository;
		
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {

		System.out.println("username88888888888888888888");

		String email = request.getEmail();
		
		String correlationId = MDC.get(CommonConstants.CORRELATIONID);
		
		String randomUUID  =  RandomGenerator.generateId();
		
		System.out.println("randomUUID" + "    "+ randomUUID);
		
		System.out.println("correlationId" + "" + correlationId);
		
		Customer customer = customerRepository.findByEmail(request.getEmail()).orElse(null);

		if (customer == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(404, "Email not found", null));

		}

		boolean passwordMatched = passwordEncoder.matches(request.getPassword(), customer.getPassword());
		
		if (!passwordMatched) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(new ApiResponse<>(401, "Invalid password", null));
		}
		
		Role role = customer.getRole();
		
		System.out.println("Role Id : " + role.getId());
		System.out.println("Role Name : " + role.getRoleName());

		String token = jwtUtil.generateToken(email,role.getRoleName());

		String refreshToken = jwtUtil.generateRefreshToken(email);

		PersonalDetails personalDetails = personalDetailsRepository.findByCustomerId(customer.getId()).orElse(null);

		LoginResponse response = LoginResponse
				.builder().status(200).message("Login Successfully").accessToken(
						token)
				.refreshToken(
						refreshToken)
				.customer(CustomerRegistrationRequest.builder().email(customer.getEmail())
						.mobileNumber(customer.getMobileNumber()).roleId(role.getId()).build())
				.personalDetails(personalDetails != null
						? PersonalDetailsDTO.builder().aadharNumber(personalDetails.getAadhar_number())
								.panCard(personalDetails.getPanCard()).address(personalDetails.getAddress()).build()
						: null)
				.build();

		return ResponseEntity.ok(response);
	}
}
