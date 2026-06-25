package com.example.demo.mapper;

import org.springframework.stereotype.Component;


import com.example.demo.dto.EmployeeRequestDTO;
import com.example.demo.dto.EmployeeResponseDTO;
import com.example.demo.entity.Employee;

@Component
public class EmployeeMapper {
	

	public Employee toEntity(EmployeeRequestDTO dto) {
        Employee emp = new Employee();

        emp.setEmpName(dto.getEmpName());
        emp.setEmpId(dto.getEmpId());
        emp.setEmpSalary(dto.getEmpSalary());
        emp.setEmpMobileNumber(dto.getEmpMobileNumber());
        emp.setEmpEmail(dto.getEmpEmail());

        return emp;
    }

    public EmployeeResponseDTO toDto(Employee emp) {
        EmployeeResponseDTO dto = new EmployeeResponseDTO();

        dto.setId(emp.getId());
        dto.setEmpName(emp.getEmpName());
        dto.setEmpId(emp.getEmpId());
        dto.setEmpSalary(emp.getEmpSalary());
        dto.setEmpMobileNumber(emp.getEmpMobileNumber());
        dto.setEmpEmail(emp.getEmpEmail());
        dto.setCreatedDateTime(emp.getCreatedDateTime());

        return dto;
    }


}
