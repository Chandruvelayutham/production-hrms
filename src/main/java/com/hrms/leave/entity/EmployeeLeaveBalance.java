package com.hrms.leave.entity;

import java.time.LocalDateTime;

import com.hrms.employee.entity.Employee;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
    name = "employee_leave_balances",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_employee_leave_year",
            columnNames = {
                "employee_id",
                "leave_type_id",
                "year"
            }
        )
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeLeaveBalance {

	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @ManyToOne(fetch = FetchType.LAZY, optional = false)
	    @JoinColumn(
	        name = "employee_id",
	        nullable = false
	    )
	    private Employee employee;

	    @ManyToOne(fetch = FetchType.LAZY, optional = false)
	    @JoinColumn(
	        name = "leave_type_id",
	        nullable = false
	    )
	    private LeaveType leaveType;

	    @Column(nullable = false)
	    private Integer year;

	    @Column(name = "allocated_days", nullable = false)
	    private Integer allocatedDays;

	    @Column(name = "used_days", nullable = false)
	    @Builder.Default
	    private Integer usedDays = 0;

	    @Column(name = "remaining_days", nullable = false)
	    private Integer remainingDays;

	    @Column(name = "created_at", updatable = false)
	    private LocalDateTime createdAt;

	    @Column(name = "updated_at")
	    private LocalDateTime updatedAt;

	    @PrePersist
	    public void onCreate() {

	        createdAt = LocalDateTime.now();
	        updatedAt = LocalDateTime.now();
	    }

	    @PreUpdate
	    public void onUpdate() {

	        updatedAt = LocalDateTime.now();
	    }
	
}
