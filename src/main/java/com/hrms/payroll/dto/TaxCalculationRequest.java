package com.hrms.payroll.dto;

import java.math.BigDecimal;

import com.hrms.payroll.enums.TaxRegime;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaxCalculationRequest {

    private BigDecimal annualGrossSalary;

    private BigDecimal annualOtherIncome;

    private BigDecimal annualOtherDeductions;

    private BigDecimal annualProfessionalTax;

    private BigDecimal annualEmployeePf;

    private BigDecimal standardDeduction;

    private TaxRegime taxRegime;

    private Integer payrollYear;
}
