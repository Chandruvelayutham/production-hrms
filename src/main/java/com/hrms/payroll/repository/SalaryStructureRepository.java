package com.hrms.payroll.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hrms.employee.entity.Employee;
import com.hrms.payroll.entity.SalaryStructure;

public interface SalaryStructureRepository
        extends JpaRepository<SalaryStructure, Long> {

    Optional<SalaryStructure> findByEmployee(Employee employee);

    boolean existsByEmployee(Employee employee);
}
