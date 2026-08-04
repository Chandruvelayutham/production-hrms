package com.hrms.dashboard.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeSummary {

	private long totalEmployees;

    private long activeEmployees;

    private long inactiveEmployees;
	
}
