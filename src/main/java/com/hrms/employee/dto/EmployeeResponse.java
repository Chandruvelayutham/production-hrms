package com.hrms.employee.dto;

import java.time.LocalDate;

import com.hrms.employee.enums.EmploymentType;
import com.hrms.employee.enums.Gender;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeResponse {

	 private Long id;

	    private String employeeCode;

	    private String firstName;

	    private String lastName;

	    private String email;

	    private String phone;

	    private LocalDate dateOfBirth;

	    private Gender gender;

	    private LocalDate joiningDate;

	    private EmploymentType employmentType;

	    private String designation;

	    private Long companyId;

	    private String companyName;

	    private Boolean active;
	    
	    private Long departmentId;

	    private String departmentName;
	
}
