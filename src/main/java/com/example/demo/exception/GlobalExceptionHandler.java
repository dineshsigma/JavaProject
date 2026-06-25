package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import com.example.demo.exception.NoDataFoundException;
import com.example.demo.exception.ErrorResponse;

import java.util.HashMap;
import java.util.Map;
import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

	// common method code for all exceptions build response

	private ResponseEntity<ErrorResponse> buildResponse(HttpStatus status, String error, String message,
			HttpServletRequest request) {
		ErrorResponse response = new ErrorResponse(status.value(), error, message, request.getRequestURI());
		return new ResponseEntity<>(response, status);
	}

//	When request body is present but validation fails (@Valid / @NotBlank etc.)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest request) {

		StringBuilder errorMessage = new StringBuilder();

		ex.getBindingResult().getFieldErrors().forEach(error -> {
			errorMessage.append(error.getField()).append(": ").append(error.getDefaultMessage()).append(", ");
		});
		return buildResponse(HttpStatus.BAD_REQUEST, "Validation Failed", errorMessage.toString(), request);
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<ErrorResponse> handleMethodNotAllowed(HttpRequestMethodNotSupportedException ex,
			HttpServletRequest request) {
		return buildResponse(HttpStatus.METHOD_NOT_ALLOWED, "Method Not Allowed", ex.getMessage(), request);
	}

//    When request body is missing / invalid JSON / cannot be parsed

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ErrorResponse> handleEmptyBody(HttpMessageNotReadableException ex,
			HttpServletRequest request) {
		return buildResponse(HttpStatus.BAD_REQUEST, "BAD REQUEST", ex.getMessage(), request);
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<ErrorResponse> handleNotFoundUrl(NoHandlerFoundException ex, HttpServletRequest request) {
		return buildResponse(HttpStatus.NOT_FOUND, "NOT FOUND", ex.getMessage(), request);
	}

	@ExceptionHandler(NoDataFoundException.class)
	public ResponseEntity<ErrorResponse> handleNoDataFound(NoDataFoundException ex, HttpServletRequest request) {
		return buildResponse(HttpStatus.NO_CONTENT, "NO_CONTENT", ex.getMessage(), request);
	}
}