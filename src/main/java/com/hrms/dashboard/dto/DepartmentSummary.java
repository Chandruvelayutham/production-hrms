package com.hrms.dashboard.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepartmentSummary {

    private long totalDepartments;

    private long activeDepartments;

    private long inactiveDepartments;
}
