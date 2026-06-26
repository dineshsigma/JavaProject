package com.example.demo.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.demo.dto.EmployeeRequestDTO;
import com.example.demo.dto.EmployeeResponseDTO;
import com.example.demo.entity.ApiResponse;
import com.example.demo.entity.Employee;
//import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.service.EmployeeService;
import com.example.demo.mapper.EmployeeMapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import com.example.demo.entity.ApiResponse;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	private final EmployeeRepository repository;
	private final EmployeeMapper mapper;

	public EmployeeServiceImpl(EmployeeRepository repository, EmployeeMapper mapper) {
		this.repository = repository;
		this.mapper = mapper;
	}

	@Override
	public Employee create(EmployeeRequestDTO request) {
		Employee emp = mapper.toEntity(request);
		return repository.save(emp);
	}

	@Override
	public List<Employee> getAll() {
		return repository.findAll();
	}

	@Override
	public ApiResponse<List<EmployeeResponseDTO>> getEmployees(int pageNumber, int size, String empId) {

		Pageable pageable = PageRequest.of(pageNumber - 1, size);

		Page<Employee> employeePage;

		if (empId != null && !empId.isBlank()) {
			employeePage = repository.findByEmpIdContainingIgnoreCase(empId, pageable);
		} else {
			employeePage = repository.findAll(pageable);
		}

		List<EmployeeResponseDTO> dtos = employeePage.getContent().stream().map(mapper::toDto).toList();

		return new ApiResponse<>(200, dtos.isEmpty() ? "No Data Found" : "Data fetched successfully", dtos);

	}

}
