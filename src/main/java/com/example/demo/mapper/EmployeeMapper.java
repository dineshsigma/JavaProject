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

		emp.setEmpMobileNumber(dto.getEmpMobileNumber());
		emp.setEmpEmail(dto.getEmpEmail());

		Object salaryObj = dto.getEmpSalary();

		if (salaryObj instanceof String) {
			emp.setEmpSalary((String) salaryObj);
		}

		return emp;
	}

	public EmployeeResponseDTO toDto(Employee emp) {
		EmployeeResponseDTO dto = new EmployeeResponseDTO();

		
//		Only required fields go to client if any security filds no need to add here like password,API keys like that
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
