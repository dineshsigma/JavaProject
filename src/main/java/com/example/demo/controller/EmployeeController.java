package com.example.demo.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.EmployeeRequestDTO;
import com.example.demo.dto.EmployeeResponseDTO;
import com.example.demo.entity.Employee;
import com.example.demo.mapper.EmployeeMapper;
import com.example.demo.service.EmployeeService;

import jakarta.validation.Valid;
import com.example.demo.entity.ApiResponse;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

	private final EmployeeService employeeservice;

	private final EmployeeMapper mapper;

	public EmployeeController(EmployeeService service, EmployeeMapper mapper) {
		this.employeeservice = service;
		this.mapper = mapper;
	}

	@GetMapping
	public ResponseEntity<ApiResponse<List<EmployeeResponseDTO>>> getAll(
			@RequestParam(defaultValue = "1") int pageNumber, @RequestParam(defaultValue = "1") int size,
			@RequestParam(required = false) String empId

	) {

//		List<Employee> employees = employeeservice.getAll(); // call service layer and fetch data from database and
//																// return a list of entity objects.
//
//		List<EmployeeResponseDTO> dtos = employees.stream().map(mapper::toDto).toList();
//
//		if (dtos.isEmpty()) {
//			return ResponseEntity.ok(new ApiResponse<>(200, "No Data Found", dtos));
//		}
//		return ResponseEntity.ok(new ApiResponse<>(200, "Data fetched successfully", dtos));

		ApiResponse<List<EmployeeResponseDTO>> response = employeeservice.getEmployees(pageNumber, size, empId);

		return ResponseEntity.ok(response);

	}

	@PostMapping
	public ResponseEntity<ApiResponse<EmployeeResponseDTO>> createEmployee(
			@Valid @RequestBody EmployeeRequestDTO request) {

		Employee emp = employeeservice.create(request);

		EmployeeResponseDTO responseDTO = mapper.toDto(emp); // After saving data convert for respone which fileds are
																// displyed to client

		return ResponseEntity.status(201).body(new ApiResponse<>(201, "Employee created successfully", responseDTO));

	}

}
