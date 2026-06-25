package com.example.demo.service;


import java.util.List;
import java.util.UUID;

import com.example.demo.dto.EmployeeRequestDTO;
import com.example.demo.entity.Employee;


public interface  EmployeeService {
	
	Employee create(EmployeeRequestDTO request);
//    Employee getById(UUID id);
    List<Employee> getAll();
//    Employee update(UUID id, EmployeeRequestDTO request);
//    void delete(UUID id);


}
