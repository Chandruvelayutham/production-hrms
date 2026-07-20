package com.hrms.leave.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeaveTypeRequest {

	@NotBlank(message = "Leave code is required")
	@Size(max = 20)
	private String leaveCode;

	@NotBlank(message = "Leave name is required")
	@Size(max = 100)
	private String leaveName;

	@Size(max = 255)
	private String description;

	@NotNull(message = "Maximum days is required")
	@Positive(message = "Maximum days must be greater than zero")
	private Integer maxDays;

}
