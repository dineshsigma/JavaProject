package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import com.example.demo.dto.CollegueRequestDTO;
import com.example.demo.dto.CollegueResponseDTO;
import com.example.demo.entity.ApiResponse;

import jakarta.validation.Valid;
import com.example.demo.service.CollegueService;


@RestController
@RequestMapping("/api/collegue")
@RequiredArgsConstructor
public class CollegueController {
	
	private final CollegueService collegueService;
	
	@PostMapping
	public ResponseEntity<ApiResponse<CollegueResponseDTO>> createEmployee(@Valid @RequestBody CollegueRequestDTO request) {
		
		ApiResponse<CollegueResponseDTO> response  = collegueService.createCollegue(request);
		
		return ResponseEntity.ok(response);
		
	}

}
