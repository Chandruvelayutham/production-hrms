package com.hrms.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.hrms.auth.dto.LoginRequest;
import com.hrms.auth.dto.LoginResponse;
import com.hrms.auth.dto.RegisterRequest;
import com.hrms.auth.service.AuthenticationService;
import com.hrms.common.response.ApiResponse;
import com.hrms.auth.dto.RefreshTokenRequest;
import com.hrms.auth.dto.RefreshTokenResponse;

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
	    
	    @PostMapping("/login")
	    public ResponseEntity<ApiResponse<LoginResponse>> login(
	            @Valid @RequestBody LoginRequest request) {

	        LoginResponse response = authenticationService.login(request);

	        return ResponseEntity.ok(
	                ApiResponse.success("Login successful", response));
	    }
	    
	    @PostMapping("/refresh")
	    public ResponseEntity<ApiResponse<RefreshTokenResponse>> refreshToken(
	            @Valid @RequestBody RefreshTokenRequest request) {

	        RefreshTokenResponse response =
	                authenticationService.refreshToken(request);

	        return ResponseEntity.ok(
	                ApiResponse.success(
	                        "Token refreshed successfully",
	                        response));
	    }
}
