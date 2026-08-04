package com.hrms.dashboard.dto;

import java.time.LocalDate;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardResponse {

	private Long companyId;

    private LocalDate attendanceDate;

    private EmployeeSummary employeeSummary;

    private AttendanceSummary attendanceSummary;

    private LeaveSummary leaveSummary;
    
    private DepartmentSummary departmentSummary;
}
