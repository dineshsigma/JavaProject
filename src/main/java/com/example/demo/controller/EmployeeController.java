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
import com.example.demo.exception.NoDataFoundException;

import com.example.demo.entity.ApiResponse;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
	

	private final EmployeeService service;
    private final EmployeeMapper mapper;
    

    public EmployeeController(EmployeeService service, EmployeeMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }
    

    @GetMapping
    public ResponseEntity<ApiResponse<List<EmployeeResponseDTO>>> getAll() {

        List<Employee> employees = service.getAll();
        
        List<EmployeeResponseDTO> dtos =
                employees.stream()
                        .map(mapper::toDto)
                        .toList();
        
        if(dtos.isEmpty()) {
        	return ResponseEntity.ok(
                new ApiResponse<>(200,"No Data Found",dtos)
        			);

        }
        

        return ResponseEntity.ok(
            new ApiResponse<>(
                    200,
                    "Data fetched successfully",
                    dtos
            )
    );


        
    }
    
    @PostMapping
    public ResponseEntity<ApiResponse<EmployeeResponseDTO>> createEmployee(
            @Valid @RequestBody EmployeeRequestDTO request) {

        Employee emp = service.create(request);

        EmployeeResponseDTO responseDTO = mapper.toDto(emp);
        

        return ResponseEntity.status(201).body(
            new ApiResponse<>(
                    201,
                    "Employee created successfully",
                    responseDTO
            )
            );
        
    }
    
    
}
