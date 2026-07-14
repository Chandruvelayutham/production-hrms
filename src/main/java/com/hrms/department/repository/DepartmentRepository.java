package com.hrms.department.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hrms.company.entity.Company;
import com.hrms.department.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long>{

	boolean existsByDepartmentCode(String departmentCode);

    boolean existsByDepartmentName(String departmentName);

    List<Department> findByCompany(Company company);

    List<Department> findByActiveTrue();

    Optional<Department> findByDepartmentCode(String departmentCode);
}
	

