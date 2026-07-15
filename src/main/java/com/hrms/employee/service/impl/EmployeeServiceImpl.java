package com.hrms.employee.service.impl;


import java.util.List;

import org.springframework.stereotype.Service;
import com.hrms.department.entity.Department;
import com.hrms.department.repository.DepartmentRepository;
import com.hrms.company.repository.CompanyRepository;
import com.hrms.employee.dto.EmployeeRequest;
import com.hrms.employee.dto.EmployeeResponse;
import com.hrms.employee.repository.EmployeeRepository;
import com.hrms.employee.service.EmployeeService;
import com.hrms.company.entity.Company;
import com.hrms.common.exception.DuplicateResourceException;
import com.hrms.common.exception.ResourceNotFoundException;
import com.hrms.employee.entity.Employee;
@Service
public class EmployeeServiceImpl implements EmployeeService{

	private final EmployeeRepository employeeRepository;
    private final CompanyRepository companyRepository;
    private final DepartmentRepository departmentRepository;
    public EmployeeServiceImpl(
            EmployeeRepository employeeRepository,
            CompanyRepository companyRepository,
            DepartmentRepository departmentRepository) {

        this.employeeRepository = employeeRepository;
        this.companyRepository = companyRepository;
        this.departmentRepository = departmentRepository;
    }

    @Override
    public EmployeeResponse createEmployee(EmployeeRequest request) {

        if (employeeRepository.existsByEmployeeCode(request.getEmployeeCode())) {
            throw new DuplicateResourceException("Employee code already exists.");
        }

        if (employeeRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Employee email already exists.");
        }

        Company company = companyRepository.findById(request.getCompanyId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Company not found with id: " + request.getCompanyId()));
        
        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Department not found with id: " + request.getDepartmentId()));

        Employee employee = Employee.builder()
                .employeeCode(request.getEmployeeCode())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .dateOfBirth(request.getDateOfBirth())
                .gender(request.getGender())
                .joiningDate(request.getJoiningDate())
                .employmentType(request.getEmploymentType())
                .designation(request.getDesignation())
                .company(company)
                .active(true)
                .build();

        employee.setDepartment(department);
        Employee savedEmployee = employeeRepository.save(employee);

        return mapToResponse(savedEmployee);
    }
    
    @Override
    public List<EmployeeResponse> getAllEmployees() {

        return employeeRepository.findByActiveTrue()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public EmployeeResponse getEmployeeById(Long id) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Employee not found with id: " + id));

        return mapToResponse(employee);
    }

    @Override
    public EmployeeResponse updateEmployee(Long id, EmployeeRequest request) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Employee not found with id: " + id));

        if (!employee.getEmployeeCode().equals(request.getEmployeeCode())
                && employeeRepository.existsByEmployeeCode(request.getEmployeeCode())) {

            throw new DuplicateResourceException("Employee code already exists.");
        }

        if (!employee.getEmail().equals(request.getEmail())
                && employeeRepository.existsByEmail(request.getEmail())) {

            throw new DuplicateResourceException("Employee email already exists.");
        }

        Company company = companyRepository.findById(request.getCompanyId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Company not found with id: " + request.getCompanyId()));
        
        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Department not found with id: " + request.getDepartmentId()));

        employee.setEmployeeCode(request.getEmployeeCode());
        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setEmail(request.getEmail());
        employee.setPhone(request.getPhone());
        employee.setDateOfBirth(request.getDateOfBirth());
        employee.setGender(request.getGender());
        employee.setJoiningDate(request.getJoiningDate());
        employee.setEmploymentType(request.getEmploymentType());
        employee.setDesignation(request.getDesignation());
        employee.setCompany(company);
        employee.setDepartment(department);
        

        Employee updatedEmployee = employeeRepository.save(employee);

        return mapToResponse(updatedEmployee);
    }

    @Override
    public void deleteEmployee(Long id) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Employee not found with id: " + id));

        employee.setActive(false);

        employeeRepository.save(employee);
    }
    
    
    private EmployeeResponse mapToResponse(Employee employee) {

        return EmployeeResponse.builder()
                .id(employee.getId())
                .employeeCode(employee.getEmployeeCode())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .phone(employee.getPhone())
                .dateOfBirth(employee.getDateOfBirth())
                .gender(employee.getGender())
                .joiningDate(employee.getJoiningDate())
                .employmentType(employee.getEmploymentType())
                .designation(employee.getDesignation())
                .companyId(employee.getCompany().getId())
                .companyName(employee.getCompany().getCompanyName())
                .departmentId(employee.getDepartment().getId())
                .departmentName(employee.getDepartment().getDepartmentName())
                .active(employee.getActive())
                .build();
    }
	
}
