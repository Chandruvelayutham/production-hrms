package com.hrms.payroll.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hrms.company.entity.Company;
import com.hrms.employee.entity.Employee;
import com.hrms.payroll.entity.Payroll;

public interface PayrollRepository extends JpaRepository<Payroll, Long> {

    Optional<Payroll> findByEmployeeAndPayrollYearAndPayrollMonth(
            Employee employee,
            Integer payrollYear,
            Integer payrollMonth);

    List<Payroll> findByEmployeeOrderByPayrollYearDescPayrollMonthDesc(
            Employee employee);

    List<Payroll> findByCompanyAndPayrollYearAndPayrollMonth(
            Company company,
            Integer payrollYear,
            Integer payrollMonth);

    boolean existsByEmployeeAndPayrollYearAndPayrollMonth(
            Employee employee,
            Integer payrollYear,
            Integer payrollMonth);
}
