package com.example.demo.entity;

import java.time.LocalDateTime;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonInclude;

@Data
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