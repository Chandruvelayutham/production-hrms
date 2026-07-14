package com.hrms.department.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hrms.company.repository.CompanyRepository;
import com.hrms.department.dto.DepartmentRequest;
import com.hrms.department.dto.DepartmentResponse;
import com.hrms.department.repository.DepartmentRepository;
import com.hrms.department.service.DepartmentService;
import com.hrms.company.entity.Company;
import com.hrms.common.exception.DuplicateResourceException;
import com.hrms.common.exception.ResourceNotFoundException;
import com.hrms.department.entity.Department;

@Service
public class DepartmentServiceImpl implements DepartmentService{

	private final DepartmentRepository departmentRepository;
    private final CompanyRepository companyRepository;

    public DepartmentServiceImpl(
            DepartmentRepository departmentRepository,
            CompanyRepository companyRepository) {

        this.departmentRepository = departmentRepository;
        this.companyRepository = companyRepository;
    }

    @Override
    public DepartmentResponse createDepartment(DepartmentRequest request) {

        if (departmentRepository.existsByDepartmentCode(request.getDepartmentCode())) {
            throw new DuplicateResourceException("Department code already exists.");
        }

        if (departmentRepository.existsByDepartmentName(request.getDepartmentName())) {
            throw new DuplicateResourceException("Department name already exists.");
        }

        Company company = companyRepository.findById(request.getCompanyId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Company not found with id: " + request.getCompanyId()));

        Department department = Department.builder()
                .departmentCode(request.getDepartmentCode())
                .departmentName(request.getDepartmentName())
                .description(request.getDescription())
                .company(company)
                .active(true)
                .build();

        Department savedDepartment = departmentRepository.save(department);

        return DepartmentResponse.builder()
                .id(savedDepartment.getId())
                .departmentCode(savedDepartment.getDepartmentCode())
                .departmentName(savedDepartment.getDepartmentName())
                .description(savedDepartment.getDescription())
                .companyId(savedDepartment.getCompany().getId())
                .companyName(savedDepartment.getCompany().getCompanyName())
                .active(savedDepartment.getActive())
                .build();
    }

    @Override
    public List<DepartmentResponse> getAllDepartments() {

        return departmentRepository.findByActiveTrue()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public DepartmentResponse getDepartmentById(Long id) {

        Department department = departmentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Department not found with id: " + id));

        return mapToResponse(department);
    }

    @Override
    public DepartmentResponse updateDepartment(Long id, DepartmentRequest request) {

        Department department = departmentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Department not found with id: " + id));

        if (!department.getDepartmentCode().equals(request.getDepartmentCode())
                && departmentRepository.existsByDepartmentCode(request.getDepartmentCode())) {

            throw new DuplicateResourceException("Department code already exists.");
        }

        if (!department.getDepartmentName().equals(request.getDepartmentName())
                && departmentRepository.existsByDepartmentName(request.getDepartmentName())) {

            throw new DuplicateResourceException("Department name already exists.");
        }

        Company company = companyRepository.findById(request.getCompanyId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Company not found with id: " + request.getCompanyId()));

        department.setDepartmentCode(request.getDepartmentCode());
        department.setDepartmentName(request.getDepartmentName());
        department.setDescription(request.getDescription());
        department.setCompany(company);

        Department updatedDepartment = departmentRepository.save(department);

        return mapToResponse(updatedDepartment);
    }

    @Override
    public void deleteDepartment(Long id) {

        Department department = departmentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Department not found with id: " + id));

        department.setActive(false);

        departmentRepository.save(department);
    }
    
    private DepartmentResponse mapToResponse(Department department) {

        return DepartmentResponse.builder()
                .id(department.getId())
                .departmentCode(department.getDepartmentCode())
                .departmentName(department.getDepartmentName())
                .description(department.getDescription())
                .companyId(department.getCompany().getId())
                .companyName(department.getCompany().getCompanyName())
                .active(department.getActive())
                .build();
    }
	
}
