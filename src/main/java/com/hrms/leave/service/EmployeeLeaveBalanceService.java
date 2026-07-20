package com.hrms.leave.service;

import java.util.List;

import com.hrms.leave.dto.EmployeeLeaveBalanceRequest;
import com.hrms.leave.dto.EmployeeLeaveBalanceResponse;

public interface EmployeeLeaveBalanceService {

	EmployeeLeaveBalanceResponse createBalance(EmployeeLeaveBalanceRequest request);
	
	List<EmployeeLeaveBalanceResponse> getEmployeeBalances(
	        Long employeeId,
	        Integer year);
}
