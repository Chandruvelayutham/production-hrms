package com.hrms.company.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hrms.company.entity.Company;

import java.util.Optional;
public interface CompanyRepository extends JpaRepository<Company, Long>{

	 Optional<Company> findByCompanyCode(String companyCode);

	    Optional<Company> findByEmail(String email);

	    boolean existsByCompanyCode(String companyCode);

	    boolean existsByEmail(String email);
	
}
