package com.example.demo.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CollegueRequestDTO;
import com.example.demo.dto.CollegueResponseDTO;
import com.example.demo.entity.ApiResponse;
import com.example.demo.entity.Collegue;
import com.example.demo.repository.CollegueRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CollegueService {
	private final CollegueRepository collegueRepository;

	public ApiResponse<CollegueResponseDTO> createCollegue(CollegueRequestDTO request) {

		try {

			if (collegueRepository.existsByEmail(request.getEmail())) {
				return new ApiResponse<>(HttpStatus.CONFLICT.value(), "Email already exists", null);
			}

			if (collegueRepository.existsByMobileNumber(request.getMobileNumber())) {

				return new ApiResponse<>(HttpStatus.CONFLICT.value(), "Mobile Number already exists", null);
			}

			Collegue collegue = new Collegue();

			collegue.setCollegueId("COL" + System.currentTimeMillis());
			collegue.setCollegueName(request.getCollegueName());
			collegue.setEmail(request.getEmail());
			collegue.setDepartment(request.getDepartment());
			collegue.setDesignation(request.getDesignation());
			collegue.setSalary(request.getSalary());
			collegue.setMobileNumber(request.getMobileNumber());
			collegue.setDateOfJoining(request.getDateOfJoining());
			collegue.setStatus(request.getStatus());

			collegue = collegueRepository.save(collegue);

			CollegueResponseDTO response = CollegueResponseDTO.builder().collegueId(collegue.getCollegueId())
					.collegueName(collegue.getCollegueName()).dateOfJoining(collegue.getDateOfJoining())
					.department(collegue.getDepartment()).designation(collegue.getDesignation())
					.email(collegue.getEmail()).mobileNumber(collegue.getMobileNumber()).status(collegue.getStatus())
					.salary(collegue.getSalary()).build();

			return new ApiResponse<>(HttpStatus.CREATED.value(), "Employee created successfully", response);

		} catch (Exception ex) {

			return new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to create employee", null);
		}
	}
}
