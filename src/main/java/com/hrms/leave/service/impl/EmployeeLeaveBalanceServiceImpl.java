package com.hrms.leave.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hrms.common.exception.DuplicateResourceException;
import com.hrms.common.exception.ResourceNotFoundException;
import com.hrms.employee.entity.Employee;
import com.hrms.employee.repository.EmployeeRepository;
import com.hrms.leave.dto.EmployeeLeaveBalanceRequest;
import com.hrms.leave.dto.EmployeeLeaveBalanceResponse;
import com.hrms.leave.entity.EmployeeLeaveBalance;
import com.hrms.leave.entity.LeaveType;
import com.hrms.leave.repository.EmployeeLeaveBalanceRepository;
import com.hrms.leave.repository.LeaveTypeRepository;
import com.hrms.leave.service.EmployeeLeaveBalanceService;

@Service
public class EmployeeLeaveBalanceServiceImpl implements EmployeeLeaveBalanceService {

	private final EmployeeLeaveBalanceRepository balanceRepository;

	private final EmployeeRepository employeeRepository;

	private final LeaveTypeRepository leaveTypeRepository;

	public EmployeeLeaveBalanceServiceImpl(EmployeeLeaveBalanceRepository balanceRepository,
			EmployeeRepository employeeRepository, LeaveTypeRepository leaveTypeRepository) {

		this.balanceRepository = balanceRepository;
		this.employeeRepository = employeeRepository;
		this.leaveTypeRepository = leaveTypeRepository;
	}

	@Override
	public EmployeeLeaveBalanceResponse createBalance(EmployeeLeaveBalanceRequest request) {

		Employee employee = employeeRepository.findById(request.getEmployeeId()).orElseThrow(
				() -> new ResourceNotFoundException("Employee not found with id: " + request.getEmployeeId()));

		LeaveType leaveType = leaveTypeRepository.findById(request.getLeaveTypeId()).orElseThrow(
				() -> new ResourceNotFoundException("Leave type not found with id: " + request.getLeaveTypeId()));

		if (!leaveType.getActive()) {

			throw new IllegalArgumentException("Leave type is inactive.");
		}

		if (balanceRepository.findByEmployeeAndLeaveTypeAndYear(employee, leaveType, request.getYear()).isPresent()) {

			throw new DuplicateResourceException(
					"Leave balance already exists for this employee, " + "leave type, and year.");
		}

		
		EmployeeLeaveBalance balance = EmployeeLeaveBalance.builder().employee(employee).leaveType(leaveType)
				.year(request.getYear()).allocatedDays(request.getAllocatedDays()).usedDays(0)
				.remainingDays(request.getAllocatedDays()).build();
		

		EmployeeLeaveBalance savedBalance = balanceRepository.save(balance);

		return mapToResponse(savedBalance);
	}

	@Override
	public List<EmployeeLeaveBalanceResponse> getEmployeeBalances(
	        Long employeeId,
	        Integer year) {

	    Employee employee =
	            employeeRepository.findById(employeeId)
	                    .orElseThrow(() ->
	                            new ResourceNotFoundException(
	                                    "Employee not found with id: "
	                                            + employeeId));

	    return balanceRepository
	            .findByEmployeeAndYear(employee, year)
	            .stream()
	            .map(this::mapToResponse)
	            .toList();
	}
	
	
	private EmployeeLeaveBalanceResponse mapToResponse(EmployeeLeaveBalance balance) {

		Employee employee = balance.getEmployee();

		LeaveType leaveType = balance.getLeaveType();

		return EmployeeLeaveBalanceResponse.builder().id(balance.getId()).employeeId(employee.getId())
				.employeeCode(employee.getEmployeeCode())
				.employeeName(employee.getFirstName() + " " + employee.getLastName()).leaveTypeId(leaveType.getId())
				.leaveCode(leaveType.getLeaveCode()).leaveName(leaveType.getLeaveName()).year(balance.getYear())
				.allocatedDays(balance.getAllocatedDays()).usedDays(balance.getUsedDays())
				.remainingDays(balance.getRemainingDays()).build();
	}

}
