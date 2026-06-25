package com.example.demo.service.impl;


import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.demo.dto.EmployeeRequestDTO;
import com.example.demo.entity.Employee;
//import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.service.EmployeeService;
import com.example.demo.mapper.EmployeeMapper;

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
    

//    @Override
//       public Employee getById(UUID id) {
//           return repository.findById(id)
//                   .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
//       }
//    
    

    @Override
    public List<Employee> getAll() {
        return repository.findAll();
    }

//    @Override
//    public Employee update(UUID id, EmployeeRequestDTO request) {
//        Employee emp = getById(id);
//
//        emp.setEmpName(request.getEmpName());
//        emp.setEmpId(request.getEmpId());
//        emp.setEmpSalary(request.getEmpSalary());
//        emp.setEmpMobileNumber(request.getEmpMobileNumber());
//        emp.setEmpEmail(request.getEmpEmail());
//
//        return repository.save(emp);
//    }
//
//    @Override
//    public void delete(UUID id) {
//        Employee emp = getById(id);
//        repository.delete(emp);
//    }






}
