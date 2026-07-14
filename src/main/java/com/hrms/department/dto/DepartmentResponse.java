package com.hrms.department.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepartmentResponse {

	
	private Long id;

    private String departmentCode;

    private String departmentName;

    private String description;

    private Long companyId;

    private String companyName;

    private Boolean active;
}
