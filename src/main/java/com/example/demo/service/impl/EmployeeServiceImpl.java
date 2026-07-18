package com.example.demo.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.CsvUploadResponseDTO;
import com.example.demo.dto.EmployeeRequestDTO;
import com.example.demo.dto.EmployeeResponseDTO;
import com.example.demo.entity.ApiResponse;
import com.example.demo.entity.Employee;
import com.example.demo.entity.Admin;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.service.EmployeeService;
import com.example.demo.mapper.EmployeeMapper;

import com.example.demo.repository.AdminBatchRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import com.example.demo.entity.Pagination;
import com.example.demo.exception.CsvProcessingException;
import com.example.demo.exception.DuplicateResourceException;
import java.util.Optional;
import java.util.function.Supplier;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	private final EmployeeRepository repository;
	private final EmployeeMapper mapper;
	private static final int BATCH_SIZE = 1000;
	private final AdminBatchRepository adminrepositorty;

	public EmployeeServiceImpl(EmployeeRepository repository, EmployeeMapper mapper,
			AdminBatchRepository adminrepositorty) {
		this.repository = repository;
		this.mapper = mapper;
		this.adminrepositorty = adminrepositorty;
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

	@Override
	public CsvUploadResponseDTO uploadCsv(MultipartFile file) {

		long total = 0;
		long success = 0;
		long failed = 0;

		List<Admin> batch = new ArrayList<>();

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {

			String line;

			reader.readLine(); // skip header

			while ((line = reader.readLine()) != null) {

				total++;

				try {

					String[] data = line.split(",");

					Admin admin = new Admin();

					admin.setId(UUID.fromString(data[0].replace("\"", "").trim()));

					admin.setFirstName(data[1].replace("\"", "").trim());

					admin.setLastName(data[2].replace("\"", "").trim());

					admin.setEmail(data[3].replace("\"", "").trim());

					admin.setMobileNumber(data[4].replace("\"", "").trim());

					batch.add(admin);

					if (batch.size() == BATCH_SIZE) {

						adminrepositorty.batchInsert(batch);

						success += batch.size();

						batch.clear();
					}

				} catch (Exception ex) {
					failed++;

//				    System.err.println("=================================");
//				    System.err.println("FAILED RECORD : " + line);
//				    System.err.println("ERROR MESSAGE : " + ex.getMessage());
//
//				    ex.printStackTrace();
//
//				    System.err.println("=================================");

				}
			}

			if (!batch.isEmpty()) {

				adminrepositorty.batchInsert(batch);

				success += batch.size();
			}

			return new CsvUploadResponseDTO("CSV Uploaded Successfully", total, success, failed);

		} catch (Exception ex) {

			throw new CsvProcessingException("Failed to process CSV File");
		}
	}

}
