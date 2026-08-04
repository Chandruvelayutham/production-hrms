package com.hrms.payroll.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.hrms.payroll.enums.PayrollStatus;
import com.hrms.payroll.enums.TaxRegime;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PayrollResponse {

    private Long id;

    private Long employeeId;

    private String employeeCode;

    private String employeeName;

    private Long companyId;

    private Integer payrollYear;

    private Integer payrollMonth;

    // Salary snapshot

    private BigDecimal basicSalary;

    private BigDecimal hra;

    private BigDecimal otherAllowances;

    private BigDecimal grossSalary;

    // Employee deductions

    private BigDecimal pfEmployee;

    private BigDecimal esiEmployee;

    private BigDecimal professionalTax;

    private BigDecimal tds;

    private BigDecimal otherDeductions;

    private BigDecimal totalDeductions;

    private BigDecimal netSalary;

    // Employer contributions

    private BigDecimal pfEmployer;

    private BigDecimal esiEmployer;

    private BigDecimal totalEmployerContribution;

    private TaxRegime taxRegime;

    private PayrollStatus status;

    private LocalDateTime processedAt;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
