package com.hrms.payroll.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import com.hrms.payroll.dto.TaxCalculationRequest;
import com.hrms.payroll.enums.TaxRegime;

class TaxCalculationServiceImplTest {

    private final TaxCalculationServiceImpl service =
            new TaxCalculationServiceImpl();

    @Test
    void shouldReturnZeroTaxForLowNewRegimeIncome() {

        TaxCalculationRequest request =
                TaxCalculationRequest.builder()
                        .annualGrossSalary(
                                new BigDecimal("696000"))
                        .annualOtherIncome(
                                BigDecimal.ZERO)
                        .annualOtherDeductions(
                                BigDecimal.ZERO)
                        .annualProfessionalTax(
                                BigDecimal.ZERO)
                        .annualEmployeePf(
                                BigDecimal.ZERO)
                        .standardDeduction(
                                new BigDecimal("75000"))
                        .taxRegime(TaxRegime.NEW)
                        .payrollYear(2026)
                        .build();

        BigDecimal tax =
                service.calculateAnnualTax(request);

        assertEquals(
                new BigDecimal("0.00"),
                tax);
    }

    @Test
    void shouldCalculateTaxForHigherNewRegimeIncome() {

        TaxCalculationRequest request =
                TaxCalculationRequest.builder()
                        .annualGrossSalary(
                                new BigDecimal("2000000"))
                        .annualOtherIncome(
                                BigDecimal.ZERO)
                        .annualOtherDeductions(
                                BigDecimal.ZERO)
                        .annualProfessionalTax(
                                BigDecimal.ZERO)
                        .annualEmployeePf(
                                BigDecimal.ZERO)
                        .standardDeduction(
                                new BigDecimal("75000"))
                        .taxRegime(TaxRegime.NEW)
                        .payrollYear(2026)
                        .build();

        BigDecimal tax =
                service.calculateAnnualTax(request);

        // Taxable income = 20,00,000 - 75,000
        // = 19,25,000
        //
        // 4L - 8L   = 20,000
        // 8L - 12L  = 40,000
        // 12L - 16L = 60,000
        // 16L - 19.25L = 65,000
        //
        // Tax = 1,85,000
        // Cess = 7,400
        // Total = 1,92,400

        assertEquals(
                new BigDecimal("192400.00"),
                tax);
    }

    @Test
    void shouldCalculateMonthlyTds() {

        TaxCalculationRequest request =
                TaxCalculationRequest.builder()
                        .annualGrossSalary(
                                new BigDecimal("2400000"))
                        .annualOtherIncome(
                                BigDecimal.ZERO)
                        .annualOtherDeductions(
                                BigDecimal.ZERO)
                        .annualProfessionalTax(
                                BigDecimal.ZERO)
                        .annualEmployeePf(
                                BigDecimal.ZERO)
                        .standardDeduction(
                                new BigDecimal("75000"))
                        .taxRegime(TaxRegime.NEW)
                        .payrollYear(2026)
                        .build();

        BigDecimal monthlyTds =
                service.calculateMonthlyTds(
                        request,
                        BigDecimal.ZERO,
                        12);

        assertEquals(
                new BigDecimal("13750.00"),
                monthlyTds);
    }
}
