package com.hrms.holiday.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HolidayRequest {

	@NotNull(message = "Company ID is required")
	private Long companyId;

	@NotBlank(message = "Holiday name is required")
	@Size(max = 150, message = "Holiday name cannot exceed 150 characters")
	private String holidayName;

	@NotNull(message = "Holiday date is required")
	private LocalDate holidayDate;

	@Size(max = 500, message = "Description cannot exceed 500 characters")
	private String description;

	@Builder.Default
	private Boolean optional = false;
}
