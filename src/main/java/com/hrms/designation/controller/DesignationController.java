package com.hrms.designation.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.hrms.common.response.ApiResponse;
import com.hrms.designation.dto.DesignationRequest;
import com.hrms.designation.dto.DesignationResponse;
import com.hrms.designation.service.DesignationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/designations")
@RequiredArgsConstructor
public class DesignationController {

	 private final DesignationService designationService;

	    @PostMapping
	    @PreAuthorize("hasRole('SUPER_ADMIN')")
	    public ResponseEntity<ApiResponse<DesignationResponse>> createDesignation(
	            @Valid @RequestBody DesignationRequest request) {

	        DesignationResponse response =
	                designationService.createDesignation(request);

	        return ResponseEntity.status(HttpStatus.CREATED)
	                .body(ApiResponse.success(
	                        "Designation created successfully",
	                        response));
	    }
	    
	    
	    @GetMapping
	    @PreAuthorize("hasRole('SUPER_ADMIN')")
	    public ResponseEntity<ApiResponse<List<DesignationResponse>>> getAllDesignations() {

	        List<DesignationResponse> response =
	                designationService.getAllDesignations();

	        return ResponseEntity.ok(
	                ApiResponse.success(
	                        "Designations retrieved successfully",
	                        response));
	    }
	    
	    @GetMapping("/{id}")
	    @PreAuthorize("hasRole('SUPER_ADMIN')")
	    public ResponseEntity<ApiResponse<DesignationResponse>> getDesignationById(
	            @PathVariable Long id) {

	        DesignationResponse response =
	                designationService.getDesignationById(id);

	        return ResponseEntity.ok(
	                ApiResponse.success(
	                        "Designation retrieved successfully",
	                        response));
	    }
	    
	    @PutMapping("/{id}")
	    @PreAuthorize("hasRole('SUPER_ADMIN')")
	    public ResponseEntity<ApiResponse<DesignationResponse>> updateDesignation(
	            @PathVariable Long id,
	            @Valid @RequestBody DesignationRequest request) {

	        DesignationResponse response =
	                designationService.updateDesignation(id, request);

	        return ResponseEntity.ok(
	                ApiResponse.success(
	                        "Designation updated successfully",
	                        response));
	    }
	    
	    @DeleteMapping("/{id}")
	    @PreAuthorize("hasRole('SUPER_ADMIN')")
	    public ResponseEntity<ApiResponse<Void>> deleteDesignation(
	            @PathVariable Long id) {

	        designationService.deleteDesignation(id);

	        return ResponseEntity.ok(
	                ApiResponse.success(
	                        "Designation deleted successfully",
	                        null));
	    }
	
}
