package com.hrms.company.service.impl;

import com.hrms.company.dto.CompanyRequest;
import com.hrms.company.dto.CompanyResponse;
import com.hrms.company.repository.CompanyRepository;
import com.hrms.company.service.CompanyService;
import org.springframework.stereotype.Service;
import com.hrms.company.entity.Company;
import com.hrms.common.exception.DuplicateResourceException;
import java.util.List;
import com.hrms.common.exception.ResourceNotFoundException;

@Service
public class CompanyServiceImpl implements CompanyService{

	 private final CompanyRepository companyRepository;

	    public CompanyServiceImpl(CompanyRepository companyRepository) {
	        this.companyRepository = companyRepository;
	    }

	    @Override
	    public CompanyResponse createCompany(CompanyRequest request) {

	        if (companyRepository.existsByCompanyCode(request.getCompanyCode())) {
	            throw new DuplicateResourceException("Company code already exists.");
	        }

	        if (companyRepository.existsByEmail(request.getEmail())) {
	            throw new DuplicateResourceException("Company email already exists.");
	        }

	        Company company = Company.builder()
	                .companyCode(request.getCompanyCode())
	                .companyName(request.getCompanyName())
	                .email(request.getEmail())
	                .phone(request.getPhone())
	                .website(request.getWebsite())
	                .addressLine1(request.getAddressLine1())
	                .addressLine2(request.getAddressLine2())
	                .city(request.getCity())
	                .state(request.getState())
	                .country(request.getCountry())
	                .postalCode(request.getPostalCode())
	                .logoUrl(request.getLogoUrl())
	                .active(true)
	                .build();

	        Company savedCompany = companyRepository.save(company);

	        return CompanyResponse.builder()
	                .id(savedCompany.getId())
	                .companyCode(savedCompany.getCompanyCode())
	                .companyName(savedCompany.getCompanyName())
	                .email(savedCompany.getEmail())
	                .phone(savedCompany.getPhone())
	                .website(savedCompany.getWebsite())
	                .addressLine1(savedCompany.getAddressLine1())
	                .addressLine2(savedCompany.getAddressLine2())
	                .city(savedCompany.getCity())
	                .state(savedCompany.getState())
	                .country(savedCompany.getCountry())
	                .postalCode(savedCompany.getPostalCode())
	                .logoUrl(savedCompany.getLogoUrl())
	                .active(savedCompany.getActive())
	                .build();
	    }

	    @Override
	    public List<CompanyResponse> getAllCompanies() {

	        return companyRepository.findAll()
	                .stream()
	                .map(company -> CompanyResponse.builder()
	                        .id(company.getId())
	                        .companyCode(company.getCompanyCode())
	                        .companyName(company.getCompanyName())
	                        .email(company.getEmail())
	                        .phone(company.getPhone())
	                        .website(company.getWebsite())
	                        .addressLine1(company.getAddressLine1())
	                        .addressLine2(company.getAddressLine2())
	                        .city(company.getCity())
	                        .state(company.getState())
	                        .country(company.getCountry())
	                        .postalCode(company.getPostalCode())
	                        .logoUrl(company.getLogoUrl())
	                        .active(company.getActive())
	                        .build())
	                .toList();
	    }
	    
	    @Override
	    public CompanyResponse getCompanyById(Long id) {

	        Company company = companyRepository.findById(id)
	                .orElseThrow(() ->
	                        new ResourceNotFoundException("Company not found with id: " + id));

	        return CompanyResponse.builder()
	                .id(company.getId())
	                .companyCode(company.getCompanyCode())
	                .companyName(company.getCompanyName())
	                .email(company.getEmail())
	                .phone(company.getPhone())
	                .website(company.getWebsite())
	                .addressLine1(company.getAddressLine1())
	                .addressLine2(company.getAddressLine2())
	                .city(company.getCity())
	                .state(company.getState())
	                .country(company.getCountry())
	                .postalCode(company.getPostalCode())
	                .logoUrl(company.getLogoUrl())
	                .active(company.getActive())
	                .build();
	    }

	    @Override
	    public CompanyResponse updateCompany(Long id, CompanyRequest request) {

	        Company company = companyRepository.findById(id)
	                .orElseThrow(() ->
	                        new ResourceNotFoundException("Company not found with id: " + id));

	        if (!company.getCompanyCode().equals(request.getCompanyCode())
	                && companyRepository.existsByCompanyCode(request.getCompanyCode())) {

	            throw new DuplicateResourceException("Company code already exists.");
	        }

	        if (!company.getEmail().equals(request.getEmail())
	                && companyRepository.existsByEmail(request.getEmail())) {

	            throw new DuplicateResourceException("Company email already exists.");
	        }

	        company.setCompanyCode(request.getCompanyCode());
	        company.setCompanyName(request.getCompanyName());
	        company.setEmail(request.getEmail());
	        company.setPhone(request.getPhone());
	        company.setWebsite(request.getWebsite());
	        company.setAddressLine1(request.getAddressLine1());
	        company.setAddressLine2(request.getAddressLine2());
	        company.setCity(request.getCity());
	        company.setState(request.getState());
	        company.setCountry(request.getCountry());
	        company.setPostalCode(request.getPostalCode());
	        company.setLogoUrl(request.getLogoUrl());

	        Company updatedCompany = companyRepository.save(company);

	        return CompanyResponse.builder()
	                .id(updatedCompany.getId())
	                .companyCode(updatedCompany.getCompanyCode())
	                .companyName(updatedCompany.getCompanyName())
	                .email(updatedCompany.getEmail())
	                .phone(updatedCompany.getPhone())
	                .website(updatedCompany.getWebsite())
	                .addressLine1(updatedCompany.getAddressLine1())
	                .addressLine2(updatedCompany.getAddressLine2())
	                .city(updatedCompany.getCity())
	                .state(updatedCompany.getState())
	                .country(updatedCompany.getCountry())
	                .postalCode(updatedCompany.getPostalCode())
	                .logoUrl(updatedCompany.getLogoUrl())
	                .active(updatedCompany.getActive())
	                .build();
	    }

	    @Override
	    public void deleteCompany(Long id) {

	        Company company = companyRepository.findById(id)
	                .orElseThrow(() ->
	                        new ResourceNotFoundException("Company not found with id: " + id));

	        company.setActive(false);

	        companyRepository.save(company);
	    }
	
}
