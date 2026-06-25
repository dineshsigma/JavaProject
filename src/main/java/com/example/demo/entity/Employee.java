package com.example.demo.entity;
import lombok.Data;


import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "emp")
public class Employee {
	
	@Id
	@GeneratedValue
	private UUID id;
	
	

	private String empName;
    private String empId;
    private String empSalary;
    private String empMobileNumber;
    private String empEmail;
    

    private OffsetDateTime createdDateTime;
    private OffsetDateTime updatedDateTime;
    
    

    @PrePersist
    public void onCreate() {
        this.createdDateTime = OffsetDateTime.now();
        this.updatedDateTime = OffsetDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedDateTime = OffsetDateTime.now();
    }





}
