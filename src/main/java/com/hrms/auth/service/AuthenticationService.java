package com.hrms.auth.service;

import com.hrms.auth.dto.LoginRequest;
import com.hrms.auth.dto.LoginResponse;
import com.hrms.auth.dto.RegisterRequest;
import com.hrms.auth.dto.RegisterResponse;


public interface AuthenticationService {

	RegisterResponse register(RegisterRequest request);
	LoginResponse login(LoginRequest request);
}
