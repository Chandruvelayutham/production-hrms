package com.hrms.designation.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hrms.company.entity.Company;
import com.hrms.designation.entity.Designation;

public interface DesignationRepository extends JpaRepository<Designation, Long>{

	boolean existsByDesignationCode(String designationCode);

    boolean existsByDesignationName(String designationName);

    List<Designation> findByCompany(Company company);

    List<Designation> findByActiveTrue();

    Optional<Designation> findByDesignationCode(String designationCode);
	
}
