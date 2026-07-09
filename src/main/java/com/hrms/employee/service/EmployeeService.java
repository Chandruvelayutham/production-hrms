package com.hrms.employee.service;

import com.hrms.employee.dto.EmployeeRequest;
import com.hrms.employee.dto.EmployeeResponse;

import java.util.List;

public interface EmployeeService {

	EmployeeResponse createEmployee(EmployeeRequest request);

	List<EmployeeResponse> getAllEmployees();

	EmployeeResponse getEmployeeById(Long id);

	EmployeeResponse updateEmployee(Long id, EmployeeRequest request);

	void deleteEmployee(Long id);

}
