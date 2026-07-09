package com.hrms.employee.controller;

import com.hrms.common.response.ApiResponse;
import com.hrms.employee.dto.EmployeeRequest;
import com.hrms.employee.dto.EmployeeResponse;
import com.hrms.employee.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {

	 private final EmployeeService employeeService;

	    @PostMapping
	    @PreAuthorize("hasRole('SUPER_ADMIN')")
	    public ResponseEntity<ApiResponse<EmployeeResponse>> createEmployee(
	            @Valid @RequestBody EmployeeRequest request) {

	        EmployeeResponse response = employeeService.createEmployee(request);

	        return ResponseEntity.status(HttpStatus.CREATED)
	                .body(ApiResponse.success(
	                        "Employee created successfully",
	                        response));
	    }
	    
	    
	    @GetMapping
	    @PreAuthorize("hasRole('SUPER_ADMIN')")
	    public ResponseEntity<ApiResponse<List<EmployeeResponse>>> getAllEmployees() {

	        List<EmployeeResponse> response = employeeService.getAllEmployees();

	        return ResponseEntity.ok(
	                ApiResponse.success(
	                        "Employees retrieved successfully",
	                        response));
	    }
	    
	    
	    @GetMapping("/{id}")
	    @PreAuthorize("hasRole('SUPER_ADMIN')")
	    public ResponseEntity<ApiResponse<EmployeeResponse>> getEmployeeById(
	            @PathVariable Long id) {

	        EmployeeResponse response = employeeService.getEmployeeById(id);

	        return ResponseEntity.ok(
	                ApiResponse.success(
	                        "Employee retrieved successfully",
	                        response));
	    }
	    
	    
	    @PutMapping("/{id}")
	    @PreAuthorize("hasRole('SUPER_ADMIN')")
	    public ResponseEntity<ApiResponse<EmployeeResponse>> updateEmployee(
	            @PathVariable Long id,
	            @Valid @RequestBody EmployeeRequest request) {

	        EmployeeResponse response = employeeService.updateEmployee(id, request);

	        return ResponseEntity.ok(
	                ApiResponse.success(
	                        "Employee updated successfully",
	                        response));
	    }
	    
	    
	    @DeleteMapping("/{id}")
	    @PreAuthorize("hasRole('SUPER_ADMIN')")
	    public ResponseEntity<ApiResponse<Void>> deleteEmployee(
	            @PathVariable Long id) {

	        employeeService.deleteEmployee(id);

	        return ResponseEntity.ok(
	                ApiResponse.success(
	                        "Employee deleted successfully",
	                        null));
	    }
}
