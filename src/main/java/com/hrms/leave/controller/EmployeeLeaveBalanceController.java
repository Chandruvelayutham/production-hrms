package com.hrms.leave.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.hrms.common.response.ApiResponse;
import com.hrms.leave.dto.EmployeeLeaveBalanceRequest;
import com.hrms.leave.dto.EmployeeLeaveBalanceResponse;
import com.hrms.leave.service.EmployeeLeaveBalanceService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/leave-balances")
@RequiredArgsConstructor
public class EmployeeLeaveBalanceController {

	private final EmployeeLeaveBalanceService balanceService;

    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<EmployeeLeaveBalanceResponse>>
            createBalance(
                    @Valid @RequestBody
                    EmployeeLeaveBalanceRequest request) {

        EmployeeLeaveBalanceResponse response =
                balanceService.createBalance(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.success(
                                "Leave balance created successfully",
                                response));
    }
	
    
    @GetMapping("/employee/{employeeId}")
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'SUPER_ADMIN')")
    public ResponseEntity<
            ApiResponse<List<EmployeeLeaveBalanceResponse>>>
            getEmployeeBalances(
                    @PathVariable Long employeeId,
                    @RequestParam Integer year) {

        List<EmployeeLeaveBalanceResponse> response =
                balanceService.getEmployeeBalances(
                        employeeId,
                        year);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Employee leave balances retrieved successfully",
                        response));
    }
    
}
