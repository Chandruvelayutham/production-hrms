package com.hrms.leave.service;

import java.util.List;

import com.hrms.leave.dto.LeaveTypeRequest;
import com.hrms.leave.dto.LeaveTypeResponse;

public interface LeaveTypeService {

	LeaveTypeResponse createLeaveType(LeaveTypeRequest request);

	List<LeaveTypeResponse> getAllLeaveTypes();

	LeaveTypeResponse getLeaveTypeById(Long id);

	LeaveTypeResponse updateLeaveType(Long id, LeaveTypeRequest request);

	void deleteLeaveType(Long id);

}
