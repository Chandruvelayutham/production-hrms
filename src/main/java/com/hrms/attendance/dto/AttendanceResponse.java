package com.hrms.attendance.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.hrms.attendance.enums.AttendanceStatus;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttendanceResponse {

	private Long id;

    private Long employeeId;

    private String employeeCode;

    private String employeeName;

    private LocalDate attendanceDate;

    private LocalDateTime checkIn;

    private LocalDateTime checkOut;

    private BigDecimal workingHours;

    private AttendanceStatus status;

    private String remarks;
	
}
