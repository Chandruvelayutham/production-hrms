package com.hrms.leave.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.hrms.leave.enums.LeaveStatus;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeaveApplicationResponse {

	private Long id;

    private Long employeeId;

    private String employeeCode;

    private String employeeName;

    private Long leaveTypeId;

    private String leaveCode;

    private String leaveName;

    private LocalDate startDate;

    private LocalDate endDate;

    private Integer totalDays;

    private String reason;

    private LeaveStatus status;

    private Long reviewedBy;

    private LocalDateTime reviewedAt;

    private String reviewRemarks;

    private LocalDateTime createdAt;
	
}
