package com.hrms.dashboard.service;

import java.time.LocalDate;

import com.hrms.dashboard.dto.DashboardResponse;

public interface DashboardService {

	DashboardResponse getDashboard(Long companyId, LocalDate attendanceDate);
}
