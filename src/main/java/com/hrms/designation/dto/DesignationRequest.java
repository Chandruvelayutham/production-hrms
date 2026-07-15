package com.hrms.designation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DesignationRequest {

	    @NotBlank(message = "Designation code is required")
	    @Size(max = 20)
	    private String designationCode;

	    @NotBlank(message = "Designation name is required")
	    @Size(max = 100)
	    private String designationName;

	    @Size(max = 255)
	    private String description;

	    @NotNull(message = "Company ID is required")
	    private Long companyId;
	
}
