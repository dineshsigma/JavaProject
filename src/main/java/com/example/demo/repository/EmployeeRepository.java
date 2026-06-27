package com.example.demo.repository;

import com.example.demo.entity.Employee;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;


public interface  EmployeeRepository extends JpaRepository<Employee, UUID> {
	
	Page<Employee> findByEmpIdContainingIgnoreCase(String empId, Pageable pageable);
	
	boolean existsByEmpId(String empId);
	
	Optional<Employee> findByEmpId(String empId);
	
	Optional<Employee> findById(UUID id);

}
