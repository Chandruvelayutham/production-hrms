package com.hrms.payroll.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hrms.common.response.ApiResponse;
import com.hrms.payroll.dto.SalaryStructureRequest;
import com.hrms.payroll.dto.SalaryStructureResponse;
import com.hrms.payroll.service.SalaryStructureService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/payroll/salary-structures")
@RequiredArgsConstructor
public class SalaryStructureController {

    private final SalaryStructureService salaryStructureService;

    @PostMapping
    public ResponseEntity<ApiResponse<SalaryStructureResponse>>
            createSalaryStructure(
                    @Valid @RequestBody SalaryStructureRequest request) {

        SalaryStructureResponse response =
                salaryStructureService
                        .createSalaryStructure(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        ApiResponse.success(
                                "Salary structure created successfully",
                                response));
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<ApiResponse<SalaryStructureResponse>>
            getSalaryStructure(
                    @PathVariable Long employeeId) {

        SalaryStructureResponse response =
                salaryStructureService
                        .getSalaryStructure(employeeId);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Salary structure retrieved successfully",
                        response));
    }

    @PutMapping("/employee/{employeeId}")
    public ResponseEntity<ApiResponse<SalaryStructureResponse>>
            updateSalaryStructure(
                    @PathVariable Long employeeId,
                    @Valid @RequestBody SalaryStructureRequest request) {

        SalaryStructureResponse response =
                salaryStructureService
                        .updateSalaryStructure(
                                employeeId,
                                request);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Salary structure updated successfully",
                        response));
    }
}
