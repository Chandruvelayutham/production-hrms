package com.hrms.attendance.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hrms.attendance.entity.Attendance;
import com.hrms.employee.entity.Employee;
	

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

	
	Optional<Attendance> findByEmployeeAndAttendanceDate(
            Employee employee,
            LocalDate attendanceDate);

    List<Attendance> findByEmployee(Employee employee);

    List<Attendance> findByAttendanceDate(LocalDate attendanceDate);

    boolean existsByEmployeeAndAttendanceDate(
            Employee employee,
            LocalDate attendanceDate);
}
