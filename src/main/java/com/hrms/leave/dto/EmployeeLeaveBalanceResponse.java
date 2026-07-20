package com.hrms.leave.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeLeaveBalanceResponse {

	private Long id;

    private Long employeeId;

    private String employeeCode;

    private String employeeName;

    private Long leaveTypeId;

    private String leaveCode;

    private String leaveName;

    private Integer year;

    private Integer allocatedDays;

    private Integer usedDays;

    private Integer remainingDays;
}
