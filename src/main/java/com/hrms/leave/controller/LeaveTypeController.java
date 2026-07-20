package com.hrms.leave.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.hrms.common.response.ApiResponse;
import com.hrms.leave.dto.LeaveTypeRequest;
import com.hrms.leave.dto.LeaveTypeResponse;
import com.hrms.leave.service.LeaveTypeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/leave-types")
@RequiredArgsConstructor
public class LeaveTypeController {

	private final LeaveTypeService leaveTypeService;

    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<LeaveTypeResponse>> createLeaveType(
            @Valid @RequestBody LeaveTypeRequest request) {

        LeaveTypeResponse response =
                leaveTypeService.createLeaveType(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        "Leave type created successfully",
                        response));
    }
    
    @GetMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'EMPLOYEE')")
    public ResponseEntity<ApiResponse<List<LeaveTypeResponse>>> getAllLeaveTypes() {

        List<LeaveTypeResponse> response =
                leaveTypeService.getAllLeaveTypes();

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Leave types retrieved successfully",
                        response));
    }	
    
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'EMPLOYEE')")
    public ResponseEntity<ApiResponse<LeaveTypeResponse>> getLeaveTypeById(
            @PathVariable Long id) {

        LeaveTypeResponse response =
                leaveTypeService.getLeaveTypeById(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Leave type retrieved successfully",
                        response));
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<LeaveTypeResponse>> updateLeaveType(
            @PathVariable Long id,
            @Valid @RequestBody LeaveTypeRequest request) {

        LeaveTypeResponse response =
                leaveTypeService.updateLeaveType(id, request);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Leave type updated successfully",
                        response));
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteLeaveType(
            @PathVariable Long id) {

        leaveTypeService.deleteLeaveType(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Leave type deactivated successfully",
                        null));
    }
	
}
