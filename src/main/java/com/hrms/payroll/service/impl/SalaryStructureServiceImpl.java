package com.hrms.payroll.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hrms.common.exception.DuplicateResourceException;
import com.hrms.common.exception.ResourceNotFoundException;
import com.hrms.employee.entity.Employee;
import com.hrms.employee.repository.EmployeeRepository;
import com.hrms.payroll.dto.SalaryStructureRequest;
import com.hrms.payroll.dto.SalaryStructureResponse;
import com.hrms.payroll.entity.SalaryStructure;
import com.hrms.payroll.repository.SalaryStructureRepository;
import com.hrms.payroll.service.SalaryStructureService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class SalaryStructureServiceImpl
        implements SalaryStructureService {

    private final SalaryStructureRepository salaryStructureRepository;

    private final EmployeeRepository employeeRepository;

    @Override
    public SalaryStructureResponse createSalaryStructure(
            SalaryStructureRequest request) {

        Employee employee = getEmployee(request.getEmployeeId());

        if (salaryStructureRepository.existsByEmployee(employee)) {
            throw new DuplicateResourceException(
                    "Salary structure already exists for employee id: "
                            + request.getEmployeeId());
        }

        SalaryStructure salaryStructure =
                SalaryStructure.builder()
                        .employee(employee)
                        .basicSalary(request.getBasicSalary())
                        .hra(request.getHra())
                        .otherAllowances(request.getOtherAllowances())
                        .otherDeductions(request.getOtherDeductions())
                        .pfApplicable(request.getPfApplicable())
                        .esiApplicable(request.getEsiApplicable())
                        .effectiveFrom(request.getEffectiveFrom())
                        .active(true)
                        .build();

        SalaryStructure savedSalaryStructure =
                salaryStructureRepository.save(salaryStructure);

        return mapToResponse(savedSalaryStructure);
    }

    @Override
    @Transactional(readOnly = true)
    public SalaryStructureResponse getSalaryStructure(
            Long employeeId) {

        Employee employee = getEmployee(employeeId);

        SalaryStructure salaryStructure =
                salaryStructureRepository
                        .findByEmployee(employee)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Salary structure not found for employee id: "
                                                + employeeId));

        return mapToResponse(salaryStructure);
    }

    @Override
    public SalaryStructureResponse updateSalaryStructure(
            Long employeeId,
            SalaryStructureRequest request) {

        Employee employee = getEmployee(employeeId);

        SalaryStructure salaryStructure =
                salaryStructureRepository
                        .findByEmployee(employee)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Salary structure not found for employee id: "
                                                + employeeId));

        salaryStructure.setBasicSalary(request.getBasicSalary());
        salaryStructure.setHra(request.getHra());
        salaryStructure.setOtherAllowances(
                request.getOtherAllowances());
        salaryStructure.setOtherDeductions(
                request.getOtherDeductions());
        salaryStructure.setPfApplicable(
                request.getPfApplicable());
        salaryStructure.setEsiApplicable(
                request.getEsiApplicable());
        salaryStructure.setEffectiveFrom(
                request.getEffectiveFrom());

        SalaryStructure updatedSalaryStructure =
                salaryStructureRepository.save(salaryStructure);

        return mapToResponse(updatedSalaryStructure);
    }

    private Employee getEmployee(Long employeeId) {

        return employeeRepository.findById(employeeId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Employee not found with id: "
                                        + employeeId));
    }

    private SalaryStructureResponse mapToResponse(
            SalaryStructure salaryStructure) {

        Employee employee = salaryStructure.getEmployee();

        return SalaryStructureResponse.builder()
                .id(salaryStructure.getId())
                .employeeId(employee.getId())
                .employeeCode(employee.getEmployeeCode())
                .employeeName(
                        employee.getFirstName()
                                + " "
                                + employee.getLastName())
                .basicSalary(salaryStructure.getBasicSalary())
                .hra(salaryStructure.getHra())
                .otherAllowances(
                        salaryStructure.getOtherAllowances())
                .otherDeductions(
                        salaryStructure.getOtherDeductions())
                .pfApplicable(
                        salaryStructure.getPfApplicable())
                .esiApplicable(
                        salaryStructure.getEsiApplicable())
                .effectiveFrom(
                        salaryStructure.getEffectiveFrom())
                .active(salaryStructure.getActive())
                .createdAt(salaryStructure.getCreatedAt())
                .updatedAt(salaryStructure.getUpdatedAt())
                .build();
    }
}
