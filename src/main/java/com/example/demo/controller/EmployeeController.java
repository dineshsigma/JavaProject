package com.example.demo.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.CsvUploadResponseDTO;
import com.example.demo.dto.EmployeeRequestDTO;
import com.example.demo.dto.EmployeeResponseDTO;
import com.example.demo.entity.Employee;
import com.example.demo.mapper.EmployeeMapper;
import com.example.demo.service.EmployeeService;

import jakarta.servlet.http.HttpServletRequest;
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
			@RequestParam(required = false) String empId,
			@RequestParam(defaultValue = "createdDateTime") String sortField,
			@RequestParam(defaultValue = "ASC") String sortOrder

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

		ApiResponse<List<EmployeeResponseDTO>> response = employeeservice.getEmployees(pageNumber, size, empId,
				sortField, sortOrder);

		return ResponseEntity.ok(response);

	}

	@PostMapping
	public ResponseEntity<ApiResponse<EmployeeResponseDTO>> createEmployee(
			@Valid @RequestBody EmployeeRequestDTO request) {

		try {
			Employee emp = employeeservice.create(request);

			EmployeeResponseDTO responseDTO = mapper.toDto(emp); // After saving data convert for respone which fileds
																	// aredisplyed to client
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new ApiResponse<>(HttpStatus.CREATED.value(), "Employee created successfully", responseDTO));

		} catch (RuntimeException ex) {

			return ResponseEntity.status(400).body(new ApiResponse<>(400, ex.getMessage(), null));

		}

	}

	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<EmployeeResponseDTO>> updateEmployee(@PathVariable UUID id,
			@Valid @RequestBody EmployeeRequestDTO request) {

		try {
			Employee updated = employeeservice.update(id, request);

			EmployeeResponseDTO dto = mapper.toDto(updated);

			return ResponseEntity.ok(new ApiResponse<>(HttpStatus.ACCEPTED.value(), "Employee updated successfully", dto));

		} catch (RuntimeException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), null));

		}

	}

	@PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<CsvUploadResponseDTO> uploadCsv(HttpServletRequest request,@RequestParam("file") MultipartFile file) {
		System.out.println("dsbdfb" + request.getContentType());
		return ResponseEntity.ok(employeeservice.uploadCsv(file));

	}
	
	@GetMapping("/{empId}")
	public ResponseEntity<?> getEmployees(@PathVariable(required = false) String empId){
		
		System.out.println("empId" + "" + empId);
		
		
		
		return ResponseEntity.ok("successs");
		
	}

}
