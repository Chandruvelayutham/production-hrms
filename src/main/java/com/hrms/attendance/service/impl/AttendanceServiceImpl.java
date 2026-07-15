package com.hrms.attendance.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hrms.attendance.dto.AttendanceResponse;
import com.hrms.attendance.dto.CheckInRequest;
import com.hrms.attendance.dto.CheckOutRequest;
import com.hrms.attendance.repository.AttendanceRepository;
import com.hrms.attendance.service.AttendanceService;
import com.hrms.employee.repository.EmployeeRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.hrms.attendance.entity.Attendance;
import com.hrms.attendance.enums.AttendanceStatus;
import com.hrms.common.exception.DuplicateResourceException;
import com.hrms.common.exception.ResourceNotFoundException;
import com.hrms.employee.entity.Employee;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;

@Service
public class AttendanceServiceImpl implements AttendanceService{

	private final AttendanceRepository attendanceRepository;
    private final EmployeeRepository employeeRepository;

    public AttendanceServiceImpl(
            AttendanceRepository attendanceRepository,
            EmployeeRepository employeeRepository) {

        this.attendanceRepository = attendanceRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public AttendanceResponse checkIn(CheckInRequest request) {

        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Employee not found with id: " + request.getEmployeeId()));

        LocalDate today = LocalDate.now();

        if (attendanceRepository.existsByEmployeeAndAttendanceDate(employee, today)) {
            throw new DuplicateResourceException(
                    "Employee has already checked in today.");
        }

        Attendance attendance = Attendance.builder()
                .employee(employee)
                .attendanceDate(today)
                .checkIn(LocalDateTime.now())
                .status(AttendanceStatus.PRESENT)
                .remarks(request.getRemarks())
                .build();

        Attendance savedAttendance = attendanceRepository.save(attendance);

        return mapToResponse(savedAttendance);
    }

    @Override
    public AttendanceResponse checkOut(CheckOutRequest request) {

        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Employee not found with id: " + request.getEmployeeId()));

        Attendance attendance = attendanceRepository
                .findByEmployeeAndAttendanceDate(employee, LocalDate.now())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "No check-in found for today."));

        if (attendance.getCheckOut() != null) {
            throw new DuplicateResourceException(
                    "Employee has already checked out today.");
        }

        LocalDateTime checkOutTime = LocalDateTime.now();

        attendance.setCheckOut(checkOutTime);

        Duration duration = Duration.between(
                attendance.getCheckIn(),
                checkOutTime);

        BigDecimal workingHours = BigDecimal.valueOf(
                duration.toMinutes() / 60.0)
                .setScale(2, RoundingMode.HALF_UP);

        attendance.setWorkingHours(workingHours);

        attendance.setRemarks(request.getRemarks());

        Attendance updatedAttendance =
                attendanceRepository.save(attendance);

        return mapToResponse(updatedAttendance);
    }

    @Override
    public List<AttendanceResponse> getEmployeeAttendance(Long employeeId) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Employee not found with id: " + employeeId));

        return attendanceRepository.findByEmployee(employee)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<AttendanceResponse> getTodayAttendance() {

        return attendanceRepository.findByAttendanceDate(LocalDate.now())
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
	
    private AttendanceResponse mapToResponse(Attendance attendance) {

        Employee employee = attendance.getEmployee();

        return AttendanceResponse.builder()
                .id(attendance.getId())
                .employeeId(employee.getId())
                .employeeCode(employee.getEmployeeCode())
                .employeeName(employee.getFirstName() + " " + employee.getLastName())
                .attendanceDate(attendance.getAttendanceDate())
                .checkIn(attendance.getCheckIn())
                .checkOut(attendance.getCheckOut())
                .workingHours(attendance.getWorkingHours())
                .status(attendance.getStatus())
                .remarks(attendance.getRemarks())
                .build();
    }
}
