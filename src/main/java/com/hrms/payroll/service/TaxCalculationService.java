package com.hrms.payroll.service;

import java.math.BigDecimal;

import com.hrms.payroll.dto.TaxCalculationRequest;

public interface TaxCalculationService {

    BigDecimal calculateAnnualTax(
            TaxCalculationRequest request);

    BigDecimal calculateMonthlyTds(
            TaxCalculationRequest request,
            BigDecimal taxAlreadyDeducted,
            Integer remainingMonths);
}
