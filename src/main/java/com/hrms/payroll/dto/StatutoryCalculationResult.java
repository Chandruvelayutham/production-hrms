package com.hrms.payroll.dto;

import java.math.BigDecimal;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatutoryCalculationResult {

    private BigDecimal pfEmployee;

    private BigDecimal pfEmployer;

    private BigDecimal esiEmployee;

    private BigDecimal esiEmployer;

    private BigDecimal professionalTax;

    private BigDecimal tds;
}
