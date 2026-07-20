package com.hrms.leave.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeLeaveBalanceRequest {

	@NotNull(message = "Employee ID is required")
    private Long employeeId;

    @NotNull(message = "Leave type ID is required")
    private Long leaveTypeId;

    @NotNull(message = "Year is required")
    private Integer year;

    @NotNull(message = "Allocated days are required")
    @Positive(message = "Allocated days must be greater than zero")
    private Integer allocatedDays;	
}
