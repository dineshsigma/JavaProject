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
	

	private Long totalElements;
    private Integer totalPages;
    private Integer currentPage;


	public ApiResponse(int status, String message, T data) {
		this.timestamp = LocalDateTime.now();
		this.status = status;
		this.message = message;
		this.data = data;
	}

	public ApiResponse(int status, String message, T data, long totalElements, int totalPages, int currentPage) {
		this.status = status;
		this.message = message;
		this.data = data;
		this.totalElements = totalElements;
		this.totalPages = totalPages;
		this.currentPage = currentPage;
	}

//	public LocalDateTime getTimestamp() {
//		return timestamp;
//	}
//
//	public int getStatus() {
//		return status;
//	}
//
//	public String getMessage() {
//		return message;
//	}
//
//	public T getData() {
//		return data;
//	}
//	
//	public int getcurrentPage() {
//		return currentPage;
//	}
//	
//	public int getTotalPages() {
//		return totalPages;
//	}
//	
//	public long gettotalElements() {
//		return totalElements;
//	}
}