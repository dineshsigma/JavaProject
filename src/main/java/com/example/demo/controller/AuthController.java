package com.example.demo.controller;

import com.example.demo.config.JwtUtil;

import org.springframework.web.bind.annotation.*;
import java.util.Map;
import com.example.demo.dto.LoginResponse;
import com.example.demo.dto.LoginRequest;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final JwtUtil jwtUtil;
	private final PasswordEncoder passwordEncoder;

	public AuthController(JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
		this.jwtUtil = jwtUtil;
		this.passwordEncoder = passwordEncoder;
	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {

		System.out.println("username88888888888888888888");

		String email = request.getEmail();
		String password = request.getPassword();
		String hashedPassword = passwordEncoder.encode(password);

		System.out.println("Hashed password" + hashedPassword);

		String token = jwtUtil.generateToken(email);

		String refreshToken = jwtUtil.generateRefreshToken(email);

		LoginResponse response = LoginResponse.builder().status(200).message("Login Successfully").accessToken(token)
				.refreshToken(refreshToken).build();

		return ResponseEntity.ok(response);
	}
}
