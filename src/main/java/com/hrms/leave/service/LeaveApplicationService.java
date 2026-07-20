package com.hrms.leave.service;

import java.util.List;

import com.hrms.leave.dto.LeaveApplicationRequest;
import com.hrms.leave.dto.LeaveApplicationResponse;
import com.hrms.leave.dto.LeaveReviewRequest;

public interface LeaveApplicationService {

	LeaveApplicationResponse applyLeave(LeaveApplicationRequest request);
	
	List<LeaveApplicationResponse> getEmployeeLeaveHistory(
	        Long employeeId);
	
	List<LeaveApplicationResponse> getPendingLeaveApplications();
	
	LeaveApplicationResponse reviewLeave(
	        Long leaveId,
	        LeaveReviewRequest request,
	        Long reviewerId);
	
	void cancelLeave(Long leaveId, Long employeeId);
}

