package com.hrms.payroll.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hrms.company.entity.Company;
import com.hrms.company.repository.CompanyRepository;
import com.hrms.common.exception.DuplicateResourceException;
import com.hrms.common.exception.ResourceNotFoundException;
import com.hrms.employee.entity.Employee;
import com.hrms.employee.repository.EmployeeRepository;
import com.hrms.payroll.dto.PayrollRequest;
import com.hrms.payroll.dto.PayrollResponse;
import com.hrms.payroll.dto.StatutoryCalculationResult;
import com.hrms.payroll.entity.Payroll;
import com.hrms.payroll.entity.SalaryStructure;
import com.hrms.payroll.enums.PayrollStatus;
import com.hrms.payroll.repository.PayrollRepository;
import com.hrms.payroll.repository.SalaryStructureRepository;
import com.hrms.payroll.service.PayrollService;
import com.hrms.payroll.service.StatutoryCalculationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class PayrollServiceImpl implements PayrollService {

    private final PayrollRepository payrollRepository;

    private final EmployeeRepository employeeRepository;

    private final CompanyRepository companyRepository;

    private final SalaryStructureRepository salaryStructureRepository;

    private final StatutoryCalculationService statutoryCalculationService;
    
    private LocalDateTime processedAt;

    @Override
    public PayrollResponse processPayroll(
            PayrollRequest request) {

        Employee employee = employeeRepository
                .findById(request.getEmployeeId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Employee not found with id: "
                                        + request.getEmployeeId()));

        Company company = employee.getCompany();

        if (company == null) {
            throw new ResourceNotFoundException(
                    "Company not found for employee id: "
                            + employee.getId());
        }

        boolean payrollExists =
                payrollRepository
                        .existsByEmployeeAndPayrollYearAndPayrollMonth(
                                employee,
                                request.getPayrollYear(),
                                request.getPayrollMonth());

        if (payrollExists) {
            throw new DuplicateResourceException(
                    "Payroll already exists for employee id: "
                            + employee.getId()
                            + " for "
                            + request.getPayrollMonth()
                            + "/"
                            + request.getPayrollYear());
        }

        SalaryStructure salaryStructure =
                salaryStructureRepository
                        .findByEmployee(employee)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Salary structure not found for employee id: "
                                                + employee.getId()));

        BigDecimal basicSalary =
                safe(salaryStructure.getBasicSalary());

        BigDecimal hra =
                safe(salaryStructure.getHra());

        BigDecimal otherAllowances =
                safe(salaryStructure.getOtherAllowances());

        BigDecimal otherDeductions =
                safe(salaryStructure.getOtherDeductions());

        BigDecimal grossSalary =
                basicSalary
                        .add(hra)
                        .add(otherAllowances);

        StatutoryCalculationResult statutory =
                statutoryCalculationService.calculate(
                        basicSalary,
                        grossSalary,
                        Boolean.TRUE.equals(
                                salaryStructure.getPfApplicable()),
                        Boolean.TRUE.equals(
                                salaryStructure.getEsiApplicable()),
                        request.getTaxRegime(),
                        request.getPayrollYear(),
                        request.getPayrollMonth());

        BigDecimal pfEmployee =
                safe(statutory.getPfEmployee());

        BigDecimal pfEmployer =
                safe(statutory.getPfEmployer());

        BigDecimal esiEmployee =
                safe(statutory.getEsiEmployee());

        BigDecimal esiEmployer =
                safe(statutory.getEsiEmployer());

        BigDecimal professionalTax =
                safe(statutory.getProfessionalTax());

        BigDecimal tds =
                safe(statutory.getTds());

        BigDecimal totalDeductions =
                pfEmployee
                        .add(esiEmployee)
                        .add(professionalTax)
                        .add(tds)
                        .add(otherDeductions);

        BigDecimal netSalary =
                grossSalary.subtract(totalDeductions);

        BigDecimal totalEmployerContribution =
                pfEmployer.add(esiEmployer);

        Payroll payroll = Payroll.builder()
                .employee(employee)
                .company(company)
                .payrollYear(request.getPayrollYear())
                .payrollMonth(request.getPayrollMonth())

                .basicSalary(basicSalary)
                .hra(hra)
                .otherAllowances(otherAllowances)
                .grossSalary(grossSalary)

                .pfEmployee(pfEmployee)
                .esiEmployee(esiEmployee)
                .professionalTax(professionalTax)
                .tds(tds)
                .otherDeductions(otherDeductions)
                .totalDeductions(totalDeductions)
                .netSalary(netSalary)
                
                .pfEmployer(pfEmployer)
                .esiEmployer(esiEmployer)
                .totalEmployerContribution(
                        totalEmployerContribution)

                .taxRegime(request.getTaxRegime())
                .status(PayrollStatus.PROCESSED)
                .processedAt(LocalDateTime.now())
                .build();

        Payroll savedPayroll =
                payrollRepository.save(payroll);

        return mapToResponse(savedPayroll);
    }

    @Override
    @Transactional(readOnly = true)
    public PayrollResponse getPayroll(
            Long employeeId,
            Integer payrollYear,
            Integer payrollMonth) {

        Employee employee =
                getEmployee(employeeId);

        Payroll payroll =
                payrollRepository
                        .findByEmployeeAndPayrollYearAndPayrollMonth(
                                employee,
                                payrollYear,
                                payrollMonth)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Payroll not found for employee id: "
                                                + employeeId
                                                + " for "
                                                + payrollMonth
                                                + "/"
                                                + payrollYear));

        return mapToResponse(payroll);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PayrollResponse> getEmployeePayrollHistory(
            Long employeeId) {

        Employee employee =
                getEmployee(employeeId);

        return payrollRepository
                .findByEmployeeOrderByPayrollYearDescPayrollMonthDesc(
                        employee)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PayrollResponse> getCompanyPayroll(
            Long companyId,
            Integer payrollYear,
            Integer payrollMonth) {

        Company company =
                companyRepository.findById(companyId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Company not found with id: "
                                                + companyId));

        return payrollRepository
                .findByCompanyAndPayrollYearAndPayrollMonth(
                        company,
                        payrollYear,
                        payrollMonth)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private Employee getEmployee(Long employeeId) {

        return employeeRepository
                .findById(employeeId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Employee not found with id: "
                                        + employeeId));
    }

    private BigDecimal safe(BigDecimal value) {

        return value == null
                ? BigDecimal.ZERO
                : value;
    }

    private PayrollResponse mapToResponse(
            Payroll payroll) {

        Employee employee =
                payroll.getEmployee();

        return PayrollResponse.builder()
                .id(payroll.getId())

                .employeeId(employee.getId())
                .employeeCode(employee.getEmployeeCode())
                .employeeName(
                        employee.getFirstName()
                                + " "
                                + employee.getLastName())

                .companyId(
                        payroll.getCompany().getId())

                .payrollYear(
                        payroll.getPayrollYear())

                .payrollMonth(
                        payroll.getPayrollMonth())

                .basicSalary(
                        payroll.getBasicSalary())

                .hra(
                        payroll.getHra())

                .otherAllowances(
                        payroll.getOtherAllowances())

                .grossSalary(
                        payroll.getGrossSalary())

                .pfEmployee(
                        payroll.getPfEmployee())

                .esiEmployee(
                        payroll.getEsiEmployee())

                .professionalTax(
                        payroll.getProfessionalTax())

                .tds(
                        payroll.getTds())

                .otherDeductions(
                        payroll.getOtherDeductions())

                .totalDeductions(
                        payroll.getTotalDeductions())

                .netSalary(
                        payroll.getNetSalary())

                .pfEmployer(
                        payroll.getPfEmployer())

                .esiEmployer(
                        payroll.getEsiEmployer())

                .totalEmployerContribution(
                        payroll.getTotalEmployerContribution())

                .taxRegime(
                        payroll.getTaxRegime())

                .status(
                        payroll.getStatus())

                .processedAt(
                        payroll.getProcessedAt())

                .createdAt(
                        payroll.getCreatedAt())

                .updatedAt(
                        payroll.getUpdatedAt())

                .build();
    }
	
}
