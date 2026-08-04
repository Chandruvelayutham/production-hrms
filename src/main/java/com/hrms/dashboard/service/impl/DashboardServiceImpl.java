package com.hrms.dashboard.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hrms.attendance.entity.Attendance;
import com.hrms.attendance.enums.AttendanceStatus;
import com.hrms.attendance.repository.AttendanceRepository;
import com.hrms.company.entity.Company;
import com.hrms.company.repository.CompanyRepository;
import com.hrms.dashboard.dto.AttendanceSummary;
import com.hrms.dashboard.dto.DashboardResponse;
import com.hrms.dashboard.dto.DepartmentSummary;
import com.hrms.dashboard.dto.EmployeeSummary;
import com.hrms.dashboard.dto.LeaveSummary;
import com.hrms.dashboard.service.DashboardService;
import com.hrms.employee.repository.EmployeeRepository;
import com.hrms.leave.enums.LeaveStatus;
import com.hrms.leave.repository.LeaveApplicationRepository;
import com.hrms.common.exception.ResourceNotFoundException;
import com.hrms.department.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import com.hrms.dashboard.dto.HolidaySummary;
import com.hrms.holiday.entity.Holiday;
import com.hrms.holiday.repository.HolidayRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class DashboardServiceImpl implements DashboardService{
	
	private final CompanyRepository companyRepository;

    private final EmployeeRepository employeeRepository;

    private final AttendanceRepository attendanceRepository;

    private final LeaveApplicationRepository leaveApplicationRepository;
    
    private final DepartmentRepository departmentRepository;
    
    private final HolidayRepository holidayRepository;

    @Override
    @Transactional(readOnly = true)
    public DashboardResponse getDashboard(
            Long companyId,
            LocalDate attendanceDate) {

        Company company = companyRepository.findById(companyId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Company not found with id: " + companyId));

        EmployeeSummary employeeSummary =
                buildEmployeeSummary(company);

        AttendanceSummary attendanceSummary =
                buildAttendanceSummary(
                        company,
                        attendanceDate);

        LeaveSummary leaveSummary =
                buildLeaveSummary(company);
        
        DepartmentSummary departmentSummary =
                buildDepartmentSummary(company);
        
        List<HolidaySummary> upcomingHolidays =
                buildUpcomingHolidays(company);

        return DashboardResponse.builder()
                .companyId(companyId)
                .attendanceDate(attendanceDate)
                .employeeSummary(employeeSummary)
                .attendanceSummary(attendanceSummary)
                .leaveSummary(leaveSummary)
                .departmentSummary(departmentSummary)
                .upcomingHolidays(upcomingHolidays)
                .build();
    }
    
    private List<HolidaySummary> buildUpcomingHolidays(
            Company company) {

        LocalDate today = LocalDate.now();

        LocalDate endDate = today.plusDays(30);

        List<Holiday> holidays =
                holidayRepository
                        .findByCompanyAndHolidayDateBetweenAndActiveTrue(
                                company,
                                today,
                                endDate);

        return holidays.stream()
                .map(this::mapToHolidaySummary)
                .toList();
    }
    
    
    private DepartmentSummary buildDepartmentSummary(
            Company company) {

        long totalDepartments =
                departmentRepository.countByCompany(company);

        long activeDepartments =
                departmentRepository
                        .countByCompanyAndActive(company, true);

        long inactiveDepartments =
                departmentRepository
                        .countByCompanyAndActive(company, false);

        return DepartmentSummary.builder()
                .totalDepartments(totalDepartments)
                .activeDepartments(activeDepartments)
                .inactiveDepartments(inactiveDepartments)
                .build();
    }
    
    private EmployeeSummary buildEmployeeSummary(
            Company company) {

        long totalEmployees =
                employeeRepository.countByCompany(company);

        long activeEmployees =
                employeeRepository
                        .countByCompanyAndActive(company, true);

        long inactiveEmployees =
                employeeRepository
                        .countByCompanyAndActive(company, false);

        return EmployeeSummary.builder()
                .totalEmployees(totalEmployees)
                .activeEmployees(activeEmployees)
                .inactiveEmployees(inactiveEmployees)
                .build();
    }

    private AttendanceSummary buildAttendanceSummary(
            Company company,
            LocalDate attendanceDate) {

        List<Attendance> attendanceList =
                attendanceRepository
                        .findByEmployeeCompanyAndAttendanceDate(
                                company,
                                attendanceDate);

        long present = countByStatus(
                attendanceList,
                AttendanceStatus.PRESENT);

        long absent = countByStatus(
                attendanceList,
                AttendanceStatus.ABSENT);

        long halfDay = countByStatus(
                attendanceList,
                AttendanceStatus.HALF_DAY);

        long late = countByStatus(
                attendanceList,
                AttendanceStatus.LATE);

        long onLeave = countByStatus(
                attendanceList,
                AttendanceStatus.ON_LEAVE);

        return AttendanceSummary.builder()
                .present(present)
                .absent(absent)
                .halfDay(halfDay)
                .late(late)
                .onLeave(onLeave)
                .build();
    }

    private long countByStatus(
            List<Attendance> attendanceList,
            AttendanceStatus status) {

        return attendanceList.stream()
                .filter(attendance ->
                        attendance.getStatus() == status)
                .count();
    }

    private LeaveSummary buildLeaveSummary(
            Company company) {

        long pending =
                leaveApplicationRepository
                        .countByEmployeeCompanyAndStatus(
                                company,
                                LeaveStatus.PENDING);

        long approved =
                leaveApplicationRepository
                        .countByEmployeeCompanyAndStatus(
                                company,
                                LeaveStatus.APPROVED);

        long rejected =
                leaveApplicationRepository
                        .countByEmployeeCompanyAndStatus(
                                company,
                                LeaveStatus.REJECTED);

        long cancelled =
                leaveApplicationRepository
                        .countByEmployeeCompanyAndStatus(
                                company,
                                LeaveStatus.CANCELLED);

        return LeaveSummary.builder()
                .pending(pending)
                .approved(approved)
                .rejected(rejected)
                .cancelled(cancelled)
                .build();
    }
    
    private HolidaySummary mapToHolidaySummary(
            Holiday holiday) {

        return HolidaySummary.builder()
                .id(holiday.getId())
                .holidayName(holiday.getHolidayName())
                .holidayDate(holiday.getHolidayDate())
                .description(holiday.getDescription())
                .optional(holiday.getOptional())
                .build();
    }
}
