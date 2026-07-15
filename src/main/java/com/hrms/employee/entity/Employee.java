package com.hrms.employee.entity;


import java.time.LocalDate;
import java.time.LocalDateTime;

import com.hrms.company.entity.Company;
import com.hrms.department.entity.Department;
import com.hrms.employee.enums.EmploymentType;
import com.hrms.employee.enums.Gender;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "employees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {

	
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column(name = "employee_code", nullable = false, unique = true, length = 20)
	    private String employeeCode;

	    @Column(name = "first_name", nullable = false, length = 100)
	    private String firstName;

	    @Column(name = "last_name", nullable = false, length = 100)
	    private String lastName;

	    @Column(nullable = false, unique = true, length = 150)
	    private String email;

	    @Column(length = 15)
	    private String phone;

	    @Column(name = "date_of_birth")
	    private LocalDate dateOfBirth;

	    @Enumerated(EnumType.STRING)
	    private Gender gender;

	    @Column(name = "joining_date", nullable = false)
	    private LocalDate joiningDate;

	    @Enumerated(EnumType.STRING)
	    @Column(name = "employment_type", nullable = false)
	    private EmploymentType employmentType;

	    @Column(length = 100)
	    private String designation;

	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "company_id", nullable = false)
	    private Company company;
	    
	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "department_id")
	    private Department department;

	 
	    

	    @Builder.Default
	    private Boolean active = true;

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
