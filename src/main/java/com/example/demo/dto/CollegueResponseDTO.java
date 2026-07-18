package com.example.demo.dto;


import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CollegueResponseDTO {
	
	private String collegueName;
	private String email;
	private String mobileNumber;
	private String department;
	private String designation;
	private LocalDate dateOfJoining;
	private BigDecimal salary;
	private String collegueId;
	private String status;

}
