package com.hrms.common.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.hrms.common.response.ApiErrorResponse;
import com.hrms.common.response.ApiResponse;

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

	 @ExceptionHandler(IllegalStateException.class)
	 public ResponseEntity<ApiResponse<Void>> handleIllegalStateException(
	         IllegalStateException ex) {

	     return ResponseEntity
	             .status(HttpStatus.CONFLICT)
	             .body(
	                     ApiResponse.error(
	                             ex.getMessage()
	                     )
	             );
	 }
	 
	 @ExceptionHandler(DuplicateResourceException.class)
	 public ResponseEntity<ApiResponse<Void>>
	         handleDuplicateResourceException(
	                 DuplicateResourceException ex) {

	     return ResponseEntity
	             .status(HttpStatus.CONFLICT)
	             .body(
	                     ApiResponse.error(
	                             ex.getMessage()
	                     )
	             );
	 }
	
	
}
