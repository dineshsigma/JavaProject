package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "personal_details")
@Data
public class PersonalDetails {

	@Id
	private UUID id;

	@Column(name = "cust_id")
	private UUID customerId;

	@Column(name = "aadhar_number")
	private String aadhar_number;

	private String address;

	@Column(name = "pan_card")
	private String panCard;

}
