package com.hrms.leave.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hrms.leave.entity.LeaveType;

public interface LeaveTypeRepository extends JpaRepository<LeaveType, Long>{

	boolean existsByLeaveCode(String leaveCode);

	boolean existsByLeaveName(String leaveName);

	List<LeaveType> findByActiveTrue();

	Optional<LeaveType> findByLeaveCode(String leaveCode);

}
