package com.hrms.company.service;

import com.hrms.company.dto.CompanyRequest;
import com.hrms.company.dto.CompanyResponse;

import java.util.List;

public interface CompanyService {

	CompanyResponse createCompany(CompanyRequest request);

    List<CompanyResponse> getAllCompanies();

    CompanyResponse getCompanyById(Long id);

    CompanyResponse updateCompany(Long id, CompanyRequest request);

    void deleteCompany(Long id);
	
}
