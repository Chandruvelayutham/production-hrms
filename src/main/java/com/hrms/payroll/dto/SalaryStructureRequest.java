package com.hrms.payroll.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalaryStructureRequest {

    @NotNull(message = "Employee ID is required")
    @Positive(message = "Employee ID must be positive")
    private Long employeeId;

    @NotNull(message = "Basic salary is required")
    @DecimalMin(
            value = "0.01",
            message = "Basic salary must be greater than zero"
    )
    private BigDecimal basicSalary;

    @NotNull(message = "HRA is required")
    @DecimalMin(
            value = "0.00",
            message = "HRA cannot be negative"
    )
    private BigDecimal hra;

    @NotNull(message = "Other allowances are required")
    @DecimalMin(
            value = "0.00",
            message = "Other allowances cannot be negative"
    )
    private BigDecimal otherAllowances;

    @NotNull(message = "Other deductions are required")
    @DecimalMin(
            value = "0.00",
            message = "Other deductions cannot be negative"
    )
    private BigDecimal otherDeductions;

    @NotNull(message = "PF applicability is required")
    private Boolean pfApplicable;

    @NotNull(message = "ESI applicability is required")
    private Boolean esiApplicable;

    @NotNull(message = "Effective from date is required")
    private LocalDate effectiveFrom;
}
