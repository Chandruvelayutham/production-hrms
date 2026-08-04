package com.hrms.payroll.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalaryStructureResponse {

    private Long id;

    private Long employeeId;

    private String employeeCode;

    private String employeeName;

    private BigDecimal basicSalary;

    private BigDecimal hra;

    private BigDecimal otherAllowances;

    private BigDecimal otherDeductions;

    private Boolean pfApplicable;

    private Boolean esiApplicable;

    private LocalDate effectiveFrom;

    private Boolean active;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
