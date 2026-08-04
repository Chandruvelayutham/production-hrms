package com.hrms.payroll.dto;

import com.hrms.payroll.enums.TaxRegime;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PayrollRequest {

    @NotNull(message = "Employee ID is required")
    @Positive(message = "Employee ID must be positive")
    private Long employeeId;

    @NotNull(message = "Payroll year is required")
    @Min(value = 2000, message = "Payroll year must be valid")
    private Integer payrollYear;

    @NotNull(message = "Payroll month is required")
    @Min(value = 1, message = "Payroll month must be between 1 and 12")
    @Max(value = 12, message = "Payroll month must be between 1 and 12")
    private Integer payrollMonth;

    @NotNull(message = "Tax regime is required")
    private TaxRegime taxRegime;
}
