package com.hrms.payroll.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.hrms.employee.entity.Employee;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "salary_structures")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalaryStructure {

	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "employee_id",
            nullable = false,
            unique = true
    )
    private Employee employee;

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
    @Builder.Default
    private BigDecimal hra = BigDecimal.ZERO;

    @Column(
            name = "other_allowances",
            nullable = false,
            precision = 12,
            scale = 2
    )
    @Builder.Default
    private BigDecimal otherAllowances = BigDecimal.ZERO;

    @Column(
            name = "other_deductions",
            nullable = false,
            precision = 12,
            scale = 2
    )
    @Builder.Default
    private BigDecimal otherDeductions = BigDecimal.ZERO;

    @Column(
            name = "pf_applicable",
            nullable = false
    )
    @Builder.Default
    private Boolean pfApplicable = true;

    @Column(
            name = "esi_applicable",
            nullable = false
    )
    @Builder.Default
    private Boolean esiApplicable = false;

    @Column(
            name = "effective_from",
            nullable = false
    )
    private LocalDate effectiveFrom;

    @Column(
            name = "active",
            nullable = false
    )
    @Builder.Default
    private Boolean active = true;

    @Column(
            name = "created_at",
            nullable = false,
            updatable = false
    )
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
