package com.hrms.leave.service.impl;


import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.hrms.common.exception.ResourceNotFoundException;
import com.hrms.employee.entity.Employee;
import com.hrms.employee.repository.EmployeeRepository;
import com.hrms.leave.dto.LeaveApplicationRequest;
import com.hrms.leave.dto.LeaveApplicationResponse;
import com.hrms.leave.dto.LeaveReviewRequest;
import com.hrms.leave.entity.EmployeeLeaveBalance;
import com.hrms.leave.entity.LeaveApplication;
import com.hrms.leave.entity.LeaveType;
import com.hrms.leave.enums.LeaveStatus;
import com.hrms.leave.repository.EmployeeLeaveBalanceRepository;
import com.hrms.leave.repository.LeaveApplicationRepository;
import com.hrms.leave.repository.LeaveTypeRepository;
import com.hrms.leave.service.LeaveApplicationService;

@Service
public class LeaveApplicationServiceImpl implements LeaveApplicationService{

	private final LeaveApplicationRepository leaveApplicationRepository;

    private final EmployeeRepository employeeRepository;

    private final LeaveTypeRepository leaveTypeRepository;
    
    private final EmployeeLeaveBalanceRepository balanceRepository;

    public LeaveApplicationServiceImpl(
            LeaveApplicationRepository leaveApplicationRepository,
            EmployeeRepository employeeRepository,
            LeaveTypeRepository leaveTypeRepository,
            EmployeeLeaveBalanceRepository balanceRepository) {

        this.leaveApplicationRepository =
                leaveApplicationRepository;

        this.employeeRepository =
                employeeRepository;

        this.leaveTypeRepository =
                leaveTypeRepository;

        this.balanceRepository =
                balanceRepository;
    }

    @Override
    public LeaveApplicationResponse applyLeave(
            LeaveApplicationRequest request) {

        Employee employee =
                employeeRepository.findById(
                        request.getEmployeeId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Employee not found with id: "
                                                + request.getEmployeeId()));

        LeaveType leaveType =
                leaveTypeRepository.findById(
                        request.getLeaveTypeId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Leave type not found with id: "
                                                + request.getLeaveTypeId()));
        
        List<LeaveStatus> activeStatuses =
                List.of(
                        LeaveStatus.PENDING,
                        LeaveStatus.APPROVED
                );

        long overlappingCount =
                leaveApplicationRepository
                        .countOverlappingLeave(
                                request.getEmployeeId(),
                                request.getStartDate(),
                                request.getEndDate(),
                                activeStatuses);

        if (overlappingCount > 0) {

            throw new IllegalStateException(
                    "Employee already has an overlapping leave application.");
        }

        if (!leaveType.getActive()) {
            throw new IllegalArgumentException(
                    "Leave type is inactive.");
        }

        if (request.getStartDate()
                .isAfter(request.getEndDate())) {

            throw new IllegalStateException(
                    "Start date cannot be after end date.");
        }

        
        if (request.getEndDate()
                .isBefore(request.getStartDate())) {

            throw new IllegalArgumentException(
                    "End date cannot be before start date.");
        }
        
        int totalDays = (int) ChronoUnit.DAYS.between(
                request.getStartDate(),
                request.getEndDate()) + 1;

        LeaveApplication leaveApplication =
                LeaveApplication.builder()
                        .employee(employee)
                        .leaveType(leaveType)
                        .startDate(request.getStartDate())
                        .endDate(request.getEndDate())
                        .totalDays(totalDays)
                        .reason(request.getReason())
                        .build();

        LeaveApplication savedLeaveApplication =
                leaveApplicationRepository.save(
                        leaveApplication);

        return mapToResponse(savedLeaveApplication);
    }

    @Override
    public List<LeaveApplicationResponse> getEmployeeLeaveHistory(
            Long employeeId) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Employee not found with id: "
                                        + employeeId));

        return leaveApplicationRepository
                .findByEmployee(employee)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
    
    @Override
    public List<LeaveApplicationResponse> getPendingLeaveApplications() {

        return leaveApplicationRepository
                .findByStatus(LeaveStatus.PENDING)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
    
    @Override
    public LeaveApplicationResponse reviewLeave(
            Long leaveId,
            LeaveReviewRequest request,
            Long reviewerId) {

        LeaveApplication leaveApplication =
                leaveApplicationRepository.findById(leaveId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Leave application not found with id: "
                                                + leaveId));

        if (leaveApplication.getStatus() != LeaveStatus.PENDING) {

            throw new IllegalStateException(
                    "Only pending leave applications can be reviewed.");
        }

        if (request.getApproved()) {

        	 int currentYear =
        	            leaveApplication.getStartDate()
        	                    .getYear();

        	    EmployeeLeaveBalance balance =
        	            balanceRepository
        	                    .findByEmployeeAndLeaveTypeAndYear(
        	                            leaveApplication.getEmployee(),
        	                            leaveApplication.getLeaveType(),
        	                            currentYear)
        	                    .orElseThrow(() ->
        	                            new ResourceNotFoundException(
        	                                    "Leave balance not found for employee"));

        	    if (balance.getRemainingDays()
        	            < leaveApplication.getTotalDays()) {

        	        throw new IllegalStateException(
        	                "Insufficient leave balance.");
        	    }

        	    balance.setUsedDays(
        	            balance.getUsedDays()
        	                    + leaveApplication.getTotalDays());

        	    balance.setRemainingDays(
        	            balance.getAllocatedDays()
        	                    - balance.getUsedDays());

        	    balanceRepository.save(balance);

        	    leaveApplication.setStatus(
        	            LeaveStatus.APPROVED);
        } else {

            leaveApplication.setStatus(
                    LeaveStatus.REJECTED);
        }

        leaveApplication.setReviewedBy(reviewerId);

        leaveApplication.setReviewedAt(
                LocalDateTime.now());

        leaveApplication.setReviewRemarks(
                request.getRemarks());

        LeaveApplication updatedApplication =
                leaveApplicationRepository.save(
                        leaveApplication);

        return mapToResponse(updatedApplication);
    }
    
    
    @Override
    public void cancelLeave(
            Long leaveId,
            Long employeeId) {

        Employee employee =
                employeeRepository.findById(employeeId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Employee not found with id: "
                                                + employeeId));

        LeaveApplication leaveApplication =
                leaveApplicationRepository
                        .findByIdAndEmployee(
                                leaveId,
                                employee)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Leave application not found for this employee"));

        if (!leaveApplication.getEmployee()
                .getId()
                .equals(employeeId)) {

            throw new AccessDeniedException(
                    "You can cancel only your own leave.");
        }
        
        if (leaveApplication.getStatus()
                == LeaveStatus.REJECTED) {

            throw new IllegalStateException(
                    "Rejected leave cannot be cancelled.");
        }

        if (leaveApplication.getStatus()
                == LeaveStatus.CANCELLED) {

            throw new IllegalStateException(
                    "Leave application is already cancelled.");
        }
        
        if (leaveApplication.getStatus()
                == LeaveStatus.APPROVED) {

            int currentYear =
                    leaveApplication.getStartDate()
                            .getYear();

            EmployeeLeaveBalance balance =
                    balanceRepository
                            .findByEmployeeAndLeaveTypeAndYear(
                                    leaveApplication.getEmployee(),
                                    leaveApplication.getLeaveType(),
                                    currentYear)
                            .orElseThrow(() ->
                                    new ResourceNotFoundException(
                                            "Leave balance not found"));

            balance.setUsedDays(
                    balance.getUsedDays()
                            - leaveApplication.getTotalDays());

            balance.setRemainingDays(
                    balance.getRemainingDays()
                            + leaveApplication.getTotalDays());

            balanceRepository.save(balance);
        }


        leaveApplication.setStatus(
                LeaveStatus.CANCELLED);

        leaveApplicationRepository.save(
                leaveApplication);
        LeaveApplication saved =
                leaveApplicationRepository
                        .save(leaveApplication);
        

    }
    
    private LeaveApplicationResponse mapToResponse(
            LeaveApplication leaveApplication) {

        Employee employee =
                leaveApplication.getEmployee();

        LeaveType leaveType =
                leaveApplication.getLeaveType();

        return LeaveApplicationResponse.builder()
                .id(leaveApplication.getId())
                .employeeId(employee.getId())
                .employeeCode(employee.getEmployeeCode())
                .employeeName(
                        employee.getFirstName()
                                + " "
                                + employee.getLastName())
                .leaveTypeId(leaveType.getId())
                .leaveCode(leaveType.getLeaveCode())
                .leaveName(leaveType.getLeaveName())
                .startDate(
                        leaveApplication.getStartDate())
                .endDate(
                        leaveApplication.getEndDate())
                .totalDays(
                        leaveApplication.getTotalDays())
                .reason(
                        leaveApplication.getReason())
                .status(
                        leaveApplication.getStatus())
                .reviewedBy(
                        leaveApplication.getReviewedBy())
                .reviewedAt(
                        leaveApplication.getReviewedAt())
                .reviewRemarks(
                        leaveApplication.getReviewRemarks())
                .createdAt(
                        leaveApplication.getCreatedAt())
                .build();
    }
}
