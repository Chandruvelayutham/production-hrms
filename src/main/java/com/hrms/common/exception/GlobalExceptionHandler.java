package com.hrms.common.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
	 
	 @ExceptionHandler(MethodArgumentNotValidException.class)
	    public ResponseEntity<ApiErrorResponse> handleValidationException(
	            MethodArgumentNotValidException ex,
	            HttpServletRequest request) {

	        String message = ex.getBindingResult()
	                .getFieldErrors()
	                .stream()
	                .map(error ->
	                        error.getField()
	                                + ": "
	                                + error.getDefaultMessage())
	                .findFirst()
	                .orElse("Validation failed");

	        ApiErrorResponse response = ApiErrorResponse.builder()
	                .timestamp(LocalDateTime.now())
	                .status(HttpStatus.BAD_REQUEST.value())
	                .error("Bad Request")
	                .message(message)
	                .path(request.getRequestURI())
	                .build();

	        return ResponseEntity
	                .status(HttpStatus.BAD_REQUEST)
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
