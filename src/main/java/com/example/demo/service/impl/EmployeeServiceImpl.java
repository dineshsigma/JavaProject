package com.example.demo.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.demo.dto.EmployeeRequestDTO;
import com.example.demo.dto.EmployeeResponseDTO;
import com.example.demo.entity.ApiResponse;
import com.example.demo.entity.Employee;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.service.EmployeeService;
import com.example.demo.mapper.EmployeeMapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import com.example.demo.entity.Pagination;
import com.example.demo.exception.DuplicateResourceException;
import java.util.Optional;
import java.util.function.Supplier;

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
		// check if EMPId is already Exits or not
		if (repository.existsByEmpId(request.getEmpId())) {
			throw new RuntimeException("Employee ID already exists: " + request.getEmpId());
		}
		Employee emp = mapper.toEntity(request);
		return repository.save(emp);
	}

	@Override
	public List<Employee> getAll() {
		return repository.findAll();
	}

	private ApiResponse<List<EmployeeResponseDTO>> validateSortField(String sortField) {

		List<String> validFields = List.of("empName", "empId", "empSalary", "empEmail", "empMobileNumber",
				"createdDateTime");

		if (sortField != null && !validFields.contains(sortField)) {

			return new ApiResponse<>(400, "Invalid sorting field: " + sortField, List.of());
		}

		return null;
	}

	@Override
	public ApiResponse<List<EmployeeResponseDTO>> getEmployees(int pageNumber, int size, String empId, String sortField,
			String sortOrder) {

		ApiResponse<List<EmployeeResponseDTO>> validationError = validateSortField(sortField);

		if (validationError != null) {
			return validationError;
		}

		Sort sort = Sort.by(sortOrder.equalsIgnoreCase("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC,
				sortField != null ? sortField : "empName" // default field
		);

		Pageable pageable = PageRequest.of(pageNumber - 1, size, sort);

		Page<Employee> employeePage;

		if (empId != null && !empId.isBlank()) {
			employeePage = repository.findByEmpIdContainingIgnoreCase(empId, pageable);
		} else {
			employeePage = repository.findAll(pageable);
		}

		List<EmployeeResponseDTO> dtos = employeePage.getContent().stream().map(mapper::toDto).toList();

		Pagination pagination = new Pagination(employeePage.getNumber() + 1, employeePage.getSize(),
				employeePage.getTotalElements());

		return new ApiResponse<>(200, dtos.isEmpty() ? "No Data Found" : "Data fetched successfully", dtos, pagination);

	}

	@Override
	public Employee update(UUID id, EmployeeRequestDTO request) {

		// Step 1: Check employee exists

		Employee existing = repository.findById(id).orElse(null);

		if (existing == null) {
			throw new RuntimeException("Employee not found with id: " + id);
		}

		// Step 2: Check empId uniqueness
		System.out.println("request.getEmpId()======" + request.getEmpId()); // EMP002
		Optional<Employee> empWithSameId = repository.findByEmpId(request.getEmpId());
		
		System.out.println("condition01 ======" + empWithSameId.isPresent()); // true
		
		System.out.println("condition02 ======" + !empWithSameId.get().getId().equals(id));

		if (empWithSameId.isPresent() && !empWithSameId.get().getId().equals(id)) {

			throw new RuntimeException("Employee not found with id: " + request.getEmpId());
		}

		existing.setEmpName(request.getEmpName());
		existing.setEmpId(request.getEmpId());
		existing.setEmpSalary(String.valueOf(request.getEmpSalary()));
		existing.setEmpMobileNumber(request.getEmpMobileNumber());
		existing.setEmpEmail(request.getEmpEmail());

		return repository.save(existing);

	}

}
