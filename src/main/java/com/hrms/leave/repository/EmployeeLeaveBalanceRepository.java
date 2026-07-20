package com.hrms.leave.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hrms.employee.entity.Employee;
import com.hrms.leave.entity.EmployeeLeaveBalance;
import com.hrms.leave.entity.LeaveType;

public interface EmployeeLeaveBalanceRepository extends JpaRepository<EmployeeLeaveBalance, Long>{

	List<EmployeeLeaveBalance> findByEmployee(Employee employee);

    Optional<EmployeeLeaveBalance> findByEmployeeAndLeaveTypeAndYear(
            Employee employee,
            LeaveType leaveType,
            Integer year);

    List<EmployeeLeaveBalance> findByEmployeeAndYear(
            Employee employee,
            Integer year);
	
    boolean existsByEmployeeAndLeaveTypeAndYear(
	        Employee employee,
	        LeaveType leaveType,
	        Integer year);
}
