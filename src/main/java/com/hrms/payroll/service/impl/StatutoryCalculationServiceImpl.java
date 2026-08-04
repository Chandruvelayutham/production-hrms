package com.hrms.payroll.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.stereotype.Service;

import com.hrms.payroll.dto.StatutoryCalculationResult;
import com.hrms.payroll.enums.TaxRegime;
import com.hrms.payroll.service.StatutoryCalculationService;

@Service
public class StatutoryCalculationServiceImpl
        implements StatutoryCalculationService {

    private static final BigDecimal ZERO = BigDecimal.ZERO;

    /*
     * PF
     */
    private static final BigDecimal PF_RATE =
            new BigDecimal("0.12");

    private static final BigDecimal PF_WAGE_CEILING =
            new BigDecimal("15000.00");

    /*
     * ESI
     *
     * Employee: 0.75%
     * Employer: 3.25%
     */
    private static final BigDecimal ESI_EMPLOYEE_RATE =
            new BigDecimal("0.0075");

    private static final BigDecimal ESI_EMPLOYER_RATE =
            new BigDecimal("0.0325");

    private static final BigDecimal ESI_WAGE_CEILING =
            new BigDecimal("21000.00");

    /*
     * Current implementation is intentionally limited to
     * the standard monthly salary inputs available in v1.
     *
     * Professional Tax is kept as zero until the applicable
     * state slab/rule is configured.
     */
    @Override
    public StatutoryCalculationResult calculate(
            BigDecimal basicSalary,
            BigDecimal grossSalary,
            boolean pfApplicable,
            boolean esiApplicable,
            TaxRegime taxRegime,
            Integer payrollYear,
            Integer payrollMonth) {

        BigDecimal pfEmployee = calculatePfEmployee(
                basicSalary,
                pfApplicable);

        BigDecimal pfEmployer = calculatePfEmployer(
                basicSalary,
                pfApplicable);

        BigDecimal esiEmployee = calculateEsiEmployee(
                grossSalary,
                esiApplicable);

        BigDecimal esiEmployer = calculateEsiEmployer(
                grossSalary,
                esiApplicable);

        BigDecimal professionalTax =
                calculateProfessionalTax(grossSalary);

        BigDecimal tds =
                calculateTds(
                        grossSalary,
                        taxRegime,
                        payrollYear,
                        payrollMonth);

        return StatutoryCalculationResult.builder()
                .pfEmployee(pfEmployee)
                .pfEmployer(pfEmployer)
                .esiEmployee(esiEmployee)
                .esiEmployer(esiEmployer)
                .professionalTax(professionalTax)
                .tds(tds)
                .build();
    }

    private BigDecimal calculatePfEmployee(
            BigDecimal basicSalary,
            boolean applicable) {

        if (!applicable) {
            return ZERO;
        }

        BigDecimal pfWages =
                basicSalary.min(PF_WAGE_CEILING);

        return pfWages
                .multiply(PF_RATE)
                .setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal calculatePfEmployer(
            BigDecimal basicSalary,
            boolean applicable) {

        if (!applicable) {
            return ZERO;
        }

        BigDecimal pfWages =
                basicSalary.min(PF_WAGE_CEILING);

        return pfWages
                .multiply(PF_RATE)
                .setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateEsiEmployee(
            BigDecimal grossSalary,
            boolean applicable) {

        if (!applicable || grossSalary.compareTo(ESI_WAGE_CEILING) > 0) {
            return ZERO;
        }

        return grossSalary
                .multiply(ESI_EMPLOYEE_RATE)
                .setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateEsiEmployer(
            BigDecimal grossSalary,
            boolean applicable) {

        if (!applicable || grossSalary.compareTo(ESI_WAGE_CEILING) > 0) {
            return ZERO;
        }

        return grossSalary
                .multiply(ESI_EMPLOYER_RATE)
                .setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateProfessionalTax(
            BigDecimal grossSalary) {

        /*
         * Do not hard-code a generic Indian PT amount.
         *
         * Professional Tax is state-specific.
         *
         * This will be replaced by the configured
         * applicable state slab.
         */
        return ZERO;
    }

    private BigDecimal calculateTds(
            BigDecimal grossSalary,
            TaxRegime taxRegime,
            Integer payrollYear,
            Integer payrollMonth) {

        /*
         * TDS requires annual taxable-income context,
         * deductions/exemptions and employee declarations.
         *
         * A single month's gross salary is insufficient
         * for a production-grade TDS calculation.
         *
         * Therefore v1 returns zero until the annual
         * tax calculation context is introduced.
         */
        return ZERO;
    }
}
