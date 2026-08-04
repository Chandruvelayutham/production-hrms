package com.hrms.payroll.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.stereotype.Service;

import com.hrms.payroll.dto.TaxCalculationRequest;
import com.hrms.payroll.enums.TaxRegime;
import com.hrms.payroll.service.TaxCalculationService;

@Service
public class TaxCalculationServiceImpl
        implements TaxCalculationService {

    private static final BigDecimal ZERO =
            BigDecimal.ZERO;

    private static final BigDecimal CESS_RATE =
            new BigDecimal("0.04");

    private static final BigDecimal NEW_REGIME_REBATE =
            new BigDecimal("60000");

    private static final BigDecimal NEW_REGIME_REBATE_LIMIT =
            new BigDecimal("1200000");

    private static final BigDecimal STANDARD_DEDUCTION_NEW =
            new BigDecimal("75000");

    @Override
    public BigDecimal calculateAnnualTax(
            TaxCalculationRequest request) {

        BigDecimal taxableIncome =
                calculateTaxableIncome(request);

        if (taxableIncome.compareTo(ZERO) <= 0) {
            return ZERO;
        }

        BigDecimal tax;

        if (request.getTaxRegime() == TaxRegime.NEW) {
            tax = calculateNewRegimeTax(taxableIncome);
        } else {
            tax = calculateOldRegimeTax(taxableIncome);
        }

        return tax
                .setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public BigDecimal calculateMonthlyTds(
            TaxCalculationRequest request,
            BigDecimal taxAlreadyDeducted,
            Integer remainingMonths) {

        if (remainingMonths == null ||
                remainingMonths <= 0) {

            return ZERO;
        }

        BigDecimal annualTax =
                calculateAnnualTax(request);

        BigDecimal alreadyDeducted =
                safe(taxAlreadyDeducted);

        BigDecimal remainingTax =
                annualTax.subtract(alreadyDeducted);

        if (remainingTax.compareTo(ZERO) <= 0) {
            return ZERO;
        }

        return remainingTax
                .divide(
                        BigDecimal.valueOf(remainingMonths),
                        2,
                        RoundingMode.HALF_UP);
    }

    private BigDecimal calculateTaxableIncome(
            TaxCalculationRequest request) {

        BigDecimal annualGrossSalary =
                safe(request.getAnnualGrossSalary());

        BigDecimal annualOtherIncome =
                safe(request.getAnnualOtherIncome());

        BigDecimal standardDeduction =
                safe(request.getStandardDeduction());

        BigDecimal annualOtherDeductions =
                safe(request.getAnnualOtherDeductions());

        BigDecimal annualProfessionalTax =
                safe(request.getAnnualProfessionalTax());

        BigDecimal annualEmployeePf =
                safe(request.getAnnualEmployeePf());

        BigDecimal totalIncome =
                annualGrossSalary
                        .add(annualOtherIncome);

        BigDecimal deductions =
                standardDeduction
                        .add(annualOtherDeductions)
                        .add(annualProfessionalTax)
                        .add(annualEmployeePf);

        /*
         * For NEW regime, only deductions permitted
         * under the applicable tax rules should ultimately
         * be included.
         *
         * The first implementation therefore uses the
         * standard deduction and does not blindly treat
         * employee PF as an additional new-regime deduction.
         */
        if (request.getTaxRegime() == TaxRegime.NEW) {

            deductions =
                    standardDeduction;
        }

        BigDecimal taxableIncome =
                totalIncome.subtract(deductions);

        return taxableIncome.max(ZERO);
    }

    private BigDecimal calculateNewRegimeTax(
            BigDecimal income) {

        BigDecimal tax = ZERO;

        BigDecimal slabStart =
                new BigDecimal("400000");

        BigDecimal slabEnd =
                new BigDecimal("800000");

        if (income.compareTo(slabStart) <= 0) {
            return ZERO;
        }

        BigDecimal taxable =
                income.min(slabEnd)
                        .subtract(slabStart);

        tax = tax.add(
                taxable.multiply(
                        new BigDecimal("0.05")));

        if (income.compareTo(slabEnd) <= 0) {
            return applyNewRegimeRebateAndCess(
                    income,
                    tax);
        }

        slabStart = slabEnd;
        slabEnd = new BigDecimal("1200000");

        taxable =
                income.min(slabEnd)
                        .subtract(slabStart);

        tax = tax.add(
                taxable.multiply(
                        new BigDecimal("0.10")));

        if (income.compareTo(slabEnd) <= 0) {
            return applyNewRegimeRebateAndCess(
                    income,
                    tax);
        }

        slabStart = slabEnd;
        slabEnd = new BigDecimal("1600000");

        taxable =
                income.min(slabEnd)
                        .subtract(slabStart);

        tax = tax.add(
                taxable.multiply(
                        new BigDecimal("0.15")));

        if (income.compareTo(slabEnd) <= 0) {
            return applyNewRegimeRebateAndCess(
                    income,
                    tax);
        }

        slabStart = slabEnd;
        slabEnd = new BigDecimal("2000000");

        taxable =
                income.min(slabEnd)
                        .subtract(slabStart);

        tax = tax.add(
                taxable.multiply(
                        new BigDecimal("0.20")));

        if (income.compareTo(slabEnd) <= 0) {
            return applyNewRegimeRebateAndCess(
                    income,
                    tax);
        }

        slabStart = slabEnd;
        slabEnd = new BigDecimal("2400000");

        taxable =
                income.min(slabEnd)
                        .subtract(slabStart);

        tax = tax.add(
                taxable.multiply(
                        new BigDecimal("0.25")));

        if (income.compareTo(slabEnd) <= 0) {
            return applyNewRegimeRebateAndCess(
                    income,
                    tax);
        }

        taxable =
                income.subtract(slabEnd);

        tax = tax.add(
                taxable.multiply(
                        new BigDecimal("0.30")));

        return applyNewRegimeRebateAndCess(
                income,
                tax);
    }

    private BigDecimal applyNewRegimeRebateAndCess(
            BigDecimal income,
            BigDecimal tax) {

        if (income.compareTo(
                NEW_REGIME_REBATE_LIMIT) <= 0) {

            tax = tax.min(NEW_REGIME_REBATE);
        }

        BigDecimal cess =
                tax.multiply(CESS_RATE);

        return tax
                .add(cess)
                .setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateOldRegimeTax(
            BigDecimal income) {

        /*
         * OLD regime will be expanded with:
         * - age category
         * - applicable deductions
         * - exemptions
         * - surcharge
         * - cess
         *
         * For now this branch deliberately remains
         * conservative rather than pretending that
         * every employee follows the same old-regime
         * calculation.
         */
        return ZERO;
    }

    private BigDecimal safe(BigDecimal value) {

        return value == null
                ? ZERO
                : value;
    }
}
