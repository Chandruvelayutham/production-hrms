package com.hrms.payroll.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hrms.common.response.ApiResponse;
import com.hrms.payroll.dto.PayrollRequest;
import com.hrms.payroll.dto.PayrollResponse;
import com.hrms.payroll.service.PayrollService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/payrolls")
@RequiredArgsConstructor
public class PayrollController {

    private final PayrollService payrollService;

    @PostMapping("/process")
    public ResponseEntity<ApiResponse<PayrollResponse>>
            processPayroll(
                    @Valid @RequestBody PayrollRequest request) {

        PayrollResponse response =
                payrollService.processPayroll(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        ApiResponse.success(
                                "Payroll processed successfully",
                                response));
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<ApiResponse<PayrollResponse>>
            getPayroll(
                    @PathVariable Long employeeId,
                    @RequestParam Integer year,
                    @RequestParam Integer month) {

        PayrollResponse response =
                payrollService.getPayroll(
                        employeeId,
                        year,
                        month);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Payroll retrieved successfully",
                        response));
    }

    @GetMapping("/employee/{employeeId}/history")
    public ResponseEntity<ApiResponse<List<PayrollResponse>>>
            getEmployeePayrollHistory(
                    @PathVariable Long employeeId) {

        List<PayrollResponse> response =
                payrollService
                        .getEmployeePayrollHistory(employeeId);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Payroll history retrieved successfully",
                        response));
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<ApiResponse<List<PayrollResponse>>>
            getCompanyPayroll(
                    @PathVariable Long companyId,
                    @RequestParam Integer year,
                    @RequestParam Integer month) {

        List<PayrollResponse> response =
                payrollService.getCompanyPayroll(
                        companyId,
                        year,
                        month);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Company payroll retrieved successfully",
                        response));
    }
}
