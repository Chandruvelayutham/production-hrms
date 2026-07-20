package com.hrms.leave.controller;


import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.hrms.common.response.ApiResponse;
import com.hrms.leave.dto.LeaveApplicationRequest;
import com.hrms.leave.dto.LeaveApplicationResponse;
import com.hrms.leave.dto.LeaveReviewRequest;
import com.hrms.leave.service.LeaveApplicationService;
import org.springframework.security.core.Authentication;

import com.hrms.auth.security.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/leave-applications")
@RequiredArgsConstructor
public class LeaveApplicationController {

	private final LeaveApplicationService leaveApplicationService;

    @PostMapping
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<ApiResponse<LeaveApplicationResponse>> applyLeave(
            @Valid @RequestBody LeaveApplicationRequest request) {

        LeaveApplicationResponse response =
                leaveApplicationService.applyLeave(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.success(
                                "Leave applied successfully",
                                response));
    }
    
    @GetMapping("/employee/{employeeId}")
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<List<LeaveApplicationResponse>>>
            getEmployeeLeaveHistory(
                    @PathVariable Long employeeId) {

        List<LeaveApplicationResponse> response =
                leaveApplicationService
                        .getEmployeeLeaveHistory(employeeId);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Employee leave history retrieved successfully",
                        response));
    }
    
    @GetMapping("/pending")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<List<LeaveApplicationResponse>>>
            getPendingLeaveApplications() {

        List<LeaveApplicationResponse> response =
                leaveApplicationService
                        .getPendingLeaveApplications();

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Pending leave applications retrieved successfully",
                        response));
    }
    
    @PutMapping("/{leaveId}/review")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<LeaveApplicationResponse>>
            reviewLeave(
                    @PathVariable Long leaveId,
                    @Valid @RequestBody LeaveReviewRequest request,
                    Authentication authentication) {

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        Long reviewerId =
                userDetails.getUser().getId();

        LeaveApplicationResponse response =
                leaveApplicationService.reviewLeave(
                        leaveId,
                        request,
                        reviewerId);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Leave application reviewed successfully",
                        response));
    }
    
    
    @PutMapping("/{leaveId}/cancel")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<ApiResponse<Void>> cancelLeave(
            @PathVariable Long leaveId,
            @RequestParam Long employeeId) {

        leaveApplicationService.cancelLeave(
                leaveId,
                employeeId);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Leave cancelled successfully",
                        null));
    }
    
}
