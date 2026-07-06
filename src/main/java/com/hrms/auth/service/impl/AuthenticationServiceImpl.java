package com.hrms.auth.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hrms.auth.dto.RegisterRequest;
import com.hrms.auth.dto.RegisterResponse;
import com.hrms.auth.entity.Role;
import com.hrms.auth.entity.User;
import com.hrms.auth.repository.UserRepository;
import com.hrms.auth.service.AuthenticationService;
import com.hrms.auth.service.RoleService;
import com.hrms.common.enums.RoleName;
import com.hrms.common.exception.DuplicateResourceException;

@Service
public class AuthenticationServiceImpl  implements AuthenticationService{

	private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationServiceImpl(
            UserRepository userRepository,
            RoleService roleService,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public RegisterResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email already exists.");
        }

        if (userRepository.existsByEmployeeCode(request.getEmployeeCode())) {
            throw new DuplicateResourceException("Employee code already exists.");
        }

        Role role = roleService.findByName(RoleName.EMPLOYEE);

        User user = User.builder()
                .employeeCode(request.getEmployeeCode())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .enabled(true)
                .accountNonLocked(true)
                .accountNonExpired(true)
                .credentialsNonExpired(true)
                .build();

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user.setRole(role);

        userRepository.save(user);

        return new RegisterResponse("User registered successfully.");

    }
	
	
}
