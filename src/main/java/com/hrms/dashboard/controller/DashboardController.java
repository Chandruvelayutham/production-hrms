package com.hrms.dashboard.controller;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hrms.common.response.ApiResponse;
import com.hrms.dashboard.dto.DashboardResponse;
import com.hrms.dashboard.service.DashboardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor
public class DashboardController {

	private final DashboardService dashboardService;

    @GetMapping("/company/{companyId}")
    public ResponseEntity<ApiResponse<DashboardResponse>>
            getDashboard(
                    @PathVariable Long companyId,
                    @RequestParam
                    @DateTimeFormat(
                            iso = DateTimeFormat.ISO.DATE)
                    LocalDate date) {

        DashboardResponse response =
                dashboardService.getDashboard(
                        companyId,
                        date);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Dashboard data retrieved successfully",
                        response));
    }
}
