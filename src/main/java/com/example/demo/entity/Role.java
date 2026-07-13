package com.example.demo.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "roles")
public class Role {

	@Id
	private UUID id;

	@Column(unique = true,name = "role_name")
	private String roleName;

}
