package com.hrms.attendance.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.hrms.attendance.dto.AttendanceResponse;
import com.hrms.attendance.dto.CheckInRequest;
import com.hrms.attendance.dto.CheckOutRequest;
import com.hrms.attendance.service.AttendanceService;
import com.hrms.common.response.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/attendance")
@RequiredArgsConstructor
public class AttendanceController {

	 private final AttendanceService attendanceService;

	    @PostMapping("/check-in")
	    @PreAuthorize("hasRole('EMPLOYEE')")
	    public ResponseEntity<ApiResponse<AttendanceResponse>> checkIn(
	            @Valid @RequestBody CheckInRequest request) {

	        AttendanceResponse response =
	                attendanceService.checkIn(request);

	        return ResponseEntity.status(HttpStatus.CREATED)
	                .body(ApiResponse.success(
	                        "Check-in successful",
	                        response));
	    }
	    
	    @PostMapping("/check-out")
	    @PreAuthorize("hasRole('EMPLOYEE')")
	    public ResponseEntity<ApiResponse<AttendanceResponse>> checkOut(
	            @Valid @RequestBody CheckOutRequest request) {

	        AttendanceResponse response =
	                attendanceService.checkOut(request);

	        return ResponseEntity.ok(
	                ApiResponse.success(
	                        "Check-out successful",
	                        response));
	    }
	    
	    @GetMapping("/employee/{employeeId}")
	    @PreAuthorize("hasAnyRole('EMPLOYEE','HR','SUPER_ADMIN')")
	    public ResponseEntity<ApiResponse<List<AttendanceResponse>>> getEmployeeAttendance(
	            @PathVariable Long employeeId) {

	        List<AttendanceResponse> response =
	                attendanceService.getEmployeeAttendance(employeeId);

	        return ResponseEntity.ok(
	                ApiResponse.success(
	                        "Employee attendance retrieved successfully",
	                        response));
	    }
	    
	    @GetMapping("/today")
	    @PreAuthorize("hasAnyRole('HR','SUPER_ADMIN')")
	    public ResponseEntity<ApiResponse<List<AttendanceResponse>>> getTodayAttendance() {

	        List<AttendanceResponse> response =
	                attendanceService.getTodayAttendance();

	        return ResponseEntity.ok(
	                ApiResponse.success(
	                        "Today's attendance retrieved successfully",
	                        response));
	    }
	
}
