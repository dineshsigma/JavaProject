package com.example.demo.repository;

import com.example.demo.entity.Employee;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entity.Employee;
import java.util.UUID;


public interface  EmployeeRepository extends JpaRepository<Employee, UUID> {
	
	Page<Employee> findByEmpIdContainingIgnoreCase(String empId, Pageable pageable);
	
	boolean existsByEmpId(String empId);

}
