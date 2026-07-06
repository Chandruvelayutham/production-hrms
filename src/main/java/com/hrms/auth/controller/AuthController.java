package com.hrms.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.hrms.auth.dto.RegisterRequest;
import com.hrms.auth.service.AuthenticationService;
import com.hrms.common.response.ApiResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
@Validated
public class AuthController {

	
	 private final AuthenticationService authenticationService;

	    public AuthController(AuthenticationService authenticationService) {
	        this.authenticationService = authenticationService;
	    }

	    @PostMapping("/register")
	    public ResponseEntity<ApiResponse<String>> register(
	            @Valid @RequestBody RegisterRequest request) {

	        authenticationService.register(request);

	        return ResponseEntity.status(HttpStatus.CREATED)
	                .body(ApiResponse.success("User registered successfully"));
	    }
}
