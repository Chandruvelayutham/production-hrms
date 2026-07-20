package com.hrms.leave.repository;

import java.util.List;
import java.util.Optional;
import java.time.LocalDate;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hrms.leave.enums.LeaveStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import com.hrms.employee.entity.Employee;
import com.hrms.leave.entity.LeaveApplication;

public interface LeaveApplicationRepository extends JpaRepository<LeaveApplication, Long> {

	List<LeaveApplication> findByEmployee(Employee employee);

	List<LeaveApplication> findByEmployeeAndStatus(Employee employee, LeaveStatus status);

	List<LeaveApplication> findByStatus(LeaveStatus status);

	Optional<LeaveApplication> findByIdAndEmployee(Long id, Employee employee);
	
	
	
	@Query("""
	        SELECT COUNT(l)
	        FROM LeaveApplication l
	        WHERE l.employee.id = :employeeId
	        AND l.status IN :statuses
	        AND l.startDate <= :endDate
	        AND l.endDate >= :startDate
	        """)
	long countOverlappingLeave(
	        @Param("employeeId") Long employeeId,
	        @Param("startDate") LocalDate startDate,
	        @Param("endDate") LocalDate endDate,
	        @Param("statuses") List<LeaveStatus> statuses);

}
