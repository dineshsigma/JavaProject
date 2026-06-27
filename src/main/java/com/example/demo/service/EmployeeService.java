package com.example.demo.service;

import java.util.List;
import java.util.UUID;
import com.example.demo.dto.EmployeeRequestDTO;

import com.example.demo.dto.EmployeeResponseDTO;
import com.example.demo.entity.Employee;
import com.example.demo.entity.ApiResponse;

public interface EmployeeService {

	Employee create(EmployeeRequestDTO request);

	List<Employee> getAll();

	public ApiResponse<List<EmployeeResponseDTO>> getEmployees(int pageNumber, int size, String empId, String sortField,String sortOrder);
	
	public Employee update(UUID id, EmployeeRequestDTO request);
}
