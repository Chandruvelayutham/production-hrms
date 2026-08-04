package com.hrms.payroll.service;

import java.util.List;

import com.hrms.payroll.dto.PayrollRequest;
import com.hrms.payroll.dto.PayrollResponse;

public interface PayrollService {

    PayrollResponse processPayroll(
            PayrollRequest request);

    PayrollResponse getPayroll(
            Long employeeId,
            Integer payrollYear,
            Integer payrollMonth);

    List<PayrollResponse> getEmployeePayrollHistory(
            Long employeeId);

    List<PayrollResponse> getCompanyPayroll(
            Long companyId,
            Integer payrollYear,
            Integer payrollMonth);
}
