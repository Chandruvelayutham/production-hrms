package com.hrms.department.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepartmentRequest {

	 	@NotBlank(message = "Department code is required")
	    @Size(max = 20)
	    private String departmentCode;

	    @NotBlank(message = "Department name is required")
	    @Size(max = 100)
	    private String departmentName;

	    @Size(max = 255)
	    private String description;

	    @NotNull(message = "Company ID is required")
	    private Long companyId;
	
}
