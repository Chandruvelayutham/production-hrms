package com.hrms.employee.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hrms.company.entity.Company;
import com.hrms.employee.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long>{


    boolean existsByEmployeeCode(String employeeCode);

    boolean existsByEmail(String email);

    List<Employee> findByCompany(Company company);

    List<Employee> findByActiveTrue();

    Optional<Employee> findByEmployeeCode(String employeeCode);
}
