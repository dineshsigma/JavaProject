package com.example.demo.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entity.Employee;
import java.util.UUID;


public interface  EmployeeRepository extends JpaRepository<Employee, UUID> {

}
