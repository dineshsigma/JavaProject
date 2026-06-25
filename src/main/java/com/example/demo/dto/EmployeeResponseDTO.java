package com.example.demo.dto;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
public class EmployeeResponseDTO {
	

    private UUID id;
    private String empName;
    private String empId;
    private String empSalary;
    private String empMobileNumber;
    private String empEmail;
    private OffsetDateTime createdDateTime;

	
	

}
