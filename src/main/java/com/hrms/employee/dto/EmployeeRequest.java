package com.hrms.employee.dto;

import java.time.LocalDate;

import com.hrms.employee.enums.EmploymentType;
import com.hrms.employee.enums.Gender;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeRequest {

	@NotBlank(message = "Employee code is required")
    private String employeeCode;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @Email
    @NotBlank(message = "Email is required")
    private String email;

    private String phone;

    private LocalDate dateOfBirth;

    @NotNull(message = "Gender is required")
    private Gender gender;

    @NotNull(message = "Joining date is required")
    private LocalDate joiningDate;

    @NotNull(message = "Employment type is required")
    private EmploymentType employmentType;

    private String designation;

    @NotNull(message = "Company ID is required")
    private Long companyId;
    
    @NotNull(message = "Department ID is required")
    private Long departmentId;
	
}
