package com.hrms.payroll.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.hrms.company.entity.Company;
import com.hrms.employee.entity.Employee;
import com.hrms.payroll.enums.PayrollStatus;
import com.hrms.payroll.enums.TaxRegime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
    name = "payrolls",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_employee_payroll_period",
            columnNames = {
                "employee_id",
                "payroll_year",
                "payroll_month"
            }
        )
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payroll {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Column(name = "payroll_year", nullable = false)
    private Integer payrollYear;

    @Column(name = "payroll_month", nullable = false)
    private Integer payrollMonth;

    // Salary snapshot

    @Column(
        name = "basic_salary",
        nullable = false,
        precision = 12,
        scale = 2
    )
    private BigDecimal basicSalary;

    @Column(
        name = "hra",
        nullable = false,
        precision = 12,
        scale = 2
    )
    private BigDecimal hra;

    @Column(
        name = "other_allowances",
        nullable = false,
        precision = 12,
        scale = 2
    )
    private BigDecimal otherAllowances;

    @Column(
        name = "gross_salary",
        nullable = false,
        precision = 12,
        scale = 2
    )
    private BigDecimal grossSalary;

    // Employee deductions

    @Column(
        name = "pf_employee",
        nullable = false,
        precision = 12,
        scale = 2
    )
    private BigDecimal pfEmployee;

    @Column(
        name = "esi_employee",
        nullable = false,
        precision = 12,
        scale = 2
    )
    private BigDecimal esiEmployee;

    @Column(
        name = "professional_tax",
        nullable = false,
        precision = 12,
        scale = 2
    )
    private BigDecimal professionalTax;

    @Column(
        name = "tds",
        nullable = false,
        precision = 12,
        scale = 2
    )
    private BigDecimal tds;

    @Column(
        name = "other_deductions",
        nullable = false,
        precision = 12,
        scale = 2
    )
    private BigDecimal otherDeductions;

    @Column(
        name = "total_deductions",
        nullable = false,
        precision = 12,
        scale = 2
    )
    private BigDecimal totalDeductions;

    @Column(
        name = "net_salary",
        nullable = false,
        precision = 12,
        scale = 2
    )
    private BigDecimal netSalary;

    // Employer contributions

    @Column(
        name = "pf_employer",
        nullable = false,
        precision = 12,
        scale = 2
    )
    private BigDecimal pfEmployer;

    @Column(
        name = "esi_employer",
        nullable = false,
        precision = 12,
        scale = 2
    )
    private BigDecimal esiEmployer;

    @Column(
        name = "total_employer_contribution",
        nullable = false,
        precision = 12,
        scale = 2
    )
    private BigDecimal totalEmployerContribution;

    @Enumerated(EnumType.STRING)
    @Column(
        name = "tax_regime",
        nullable = false,
        length = 10
    )
    private TaxRegime taxRegime;

    @Enumerated(EnumType.STRING)
    @Column(
        name = "status",
        nullable = false,
        length = 20
    )
    @Builder.Default
    private PayrollStatus status = PayrollStatus.DRAFT;

    @Column(name = "processed_at")
    private LocalDateTime processedAt;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
