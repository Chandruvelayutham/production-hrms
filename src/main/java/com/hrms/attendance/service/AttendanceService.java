package com.hrms.attendance.service;

import java.util.List;

import com.hrms.attendance.dto.AttendanceResponse;
import com.hrms.attendance.dto.CheckInRequest;
import com.hrms.attendance.dto.CheckOutRequest;

public interface AttendanceService {

	AttendanceResponse checkIn(CheckInRequest request);

	AttendanceResponse checkOut(CheckOutRequest request);

	List<AttendanceResponse> getEmployeeAttendance(Long employeeId);

	List<AttendanceResponse> getTodayAttendance();

}
