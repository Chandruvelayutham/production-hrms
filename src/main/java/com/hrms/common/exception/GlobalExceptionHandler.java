package com.hrms.common.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.hrms.common.response.ApiErrorResponse;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

	
	 @ExceptionHandler(ResourceNotFoundException.class)
	    public ResponseEntity<ApiErrorResponse> handleResourceNotFound(
	            ResourceNotFoundException ex,
	            HttpServletRequest request) {

	        ApiErrorResponse response = new ApiErrorResponse(
	                LocalDateTime.now(),
	                HttpStatus.NOT_FOUND.value(),
	                "Not Found",
	                ex.getMessage(),
	                request.getRequestURI());

	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                .body(response);
	    }

	
	
}
