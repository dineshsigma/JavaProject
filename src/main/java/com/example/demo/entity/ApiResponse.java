package com.example.demo.entity;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

	private LocalDateTime timestamp;
	private int status;
	private String message;
	private T data;
	private Pagination pagination;
	



	public ApiResponse(int status, String message, T data) {
		this.timestamp = LocalDateTime.now();
		this.status = status;
		this.message = message;
		this.data = data;
	}

	public ApiResponse(int status, String message, T data,Pagination pagination ) {
		this.status = status;
		this.message = message;
		this.data = data;
		this.pagination = pagination;
	
	}

}