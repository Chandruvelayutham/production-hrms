package com.hrms.designation.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hrms.company.repository.CompanyRepository;
import com.hrms.designation.dto.DesignationRequest;
import com.hrms.designation.dto.DesignationResponse;
import com.hrms.designation.repository.DesignationRepository;
import com.hrms.designation.service.DesignationService;
import com.hrms.company.entity.Company;
import com.hrms.common.exception.DuplicateResourceException;
import com.hrms.common.exception.ResourceNotFoundException;
import com.hrms.designation.entity.Designation;

@Service
public class DesignationServiceImpl implements DesignationService{

	
	    private final DesignationRepository designationRepository;
	    private final CompanyRepository companyRepository;

	    public DesignationServiceImpl(
	            DesignationRepository designationRepository,
	            CompanyRepository companyRepository) {

	        this.designationRepository = designationRepository;
	        this.companyRepository = companyRepository;
	    }

	    @Override
	    public DesignationResponse createDesignation(DesignationRequest request) {

	        if (designationRepository.existsByDesignationCode(request.getDesignationCode())) {
	            throw new DuplicateResourceException("Designation code already exists.");
	        }

	        if (designationRepository.existsByDesignationName(request.getDesignationName())) {
	            throw new DuplicateResourceException("Designation name already exists.");
	        }

	        Company company = companyRepository.findById(request.getCompanyId())
	                .orElseThrow(() ->
	                        new ResourceNotFoundException(
	                                "Company not found with id: " + request.getCompanyId()));

	        Designation designation = Designation.builder()
	                .designationCode(request.getDesignationCode())
	                .designationName(request.getDesignationName())
	                .description(request.getDescription())
	                .company(company)
	                .active(true)
	                .build();

	        Designation savedDesignation = designationRepository.save(designation);

	        return mapToResponse(savedDesignation);
	    }

	    @Override
	    public List<DesignationResponse> getAllDesignations() {

	        return designationRepository.findByActiveTrue()
	                .stream()
	                .map(this::mapToResponse)
	                .toList();
	    }
	    
	    @Override
	    public DesignationResponse getDesignationById(Long id) {

	        Designation designation = designationRepository.findById(id)
	                .orElseThrow(() ->
	                        new ResourceNotFoundException(
	                                "Designation not found with id: " + id));

	        return mapToResponse(designation);
	    }

	    @Override
	    public DesignationResponse updateDesignation(
	            Long id,
	            DesignationRequest request) {

	        Designation designation = designationRepository.findById(id)
	                .orElseThrow(() ->
	                        new ResourceNotFoundException(
	                                "Designation not found with id: " + id));

	        if (!designation.getDesignationCode().equals(request.getDesignationCode())
	                && designationRepository.existsByDesignationCode(request.getDesignationCode())) {

	            throw new DuplicateResourceException("Designation code already exists.");
	        }

	        if (!designation.getDesignationName().equals(request.getDesignationName())
	                && designationRepository.existsByDesignationName(request.getDesignationName())) {

	            throw new DuplicateResourceException("Designation name already exists.");
	        }

	        Company company = companyRepository.findById(request.getCompanyId())
	                .orElseThrow(() ->
	                        new ResourceNotFoundException(
	                                "Company not found with id: " + request.getCompanyId()));

	        designation.setDesignationCode(request.getDesignationCode());
	        designation.setDesignationName(request.getDesignationName());
	        designation.setDescription(request.getDescription());
	        designation.setCompany(company);

	        Designation updatedDesignation = designationRepository.save(designation);

	        return mapToResponse(updatedDesignation);
	    }

	    @Override
	    public void deleteDesignation(Long id) {

	        Designation designation = designationRepository.findById(id)
	                .orElseThrow(() ->
	                        new ResourceNotFoundException(
	                                "Designation not found with id: " + id));

	        designation.setActive(false);

	        designationRepository.save(designation);
	    }
	    
	    private DesignationResponse mapToResponse(Designation designation) {

	        return DesignationResponse.builder()
	                .id(designation.getId())
	                .designationCode(designation.getDesignationCode())
	                .designationName(designation.getDesignationName())
	                .description(designation.getDescription())
	                .companyId(designation.getCompany().getId())
	                .companyName(designation.getCompany().getCompanyName())
	                .active(designation.getActive())
	                .build();
	    }
	
}
