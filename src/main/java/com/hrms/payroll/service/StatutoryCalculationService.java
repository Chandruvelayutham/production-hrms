package com.hrms.payroll.service;

import java.math.BigDecimal;

import com.hrms.payroll.dto.StatutoryCalculationResult;
import com.hrms.payroll.enums.TaxRegime;

public interface StatutoryCalculationService {

    StatutoryCalculationResult calculate(
            BigDecimal basicSalary,
            BigDecimal grossSalary,
            boolean pfApplicable,
            boolean esiApplicable,
            TaxRegime taxRegime,
            Integer payrollYear,
            Integer payrollMonth);
}
