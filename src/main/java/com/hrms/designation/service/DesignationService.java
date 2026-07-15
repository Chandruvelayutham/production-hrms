package com.hrms.designation.service;

import java.util.List;

import com.hrms.designation.dto.DesignationRequest;
import com.hrms.designation.dto.DesignationResponse;


public interface DesignationService {

	DesignationResponse createDesignation(DesignationRequest request);

    List<DesignationResponse> getAllDesignations();

    DesignationResponse getDesignationById(Long id);

    DesignationResponse updateDesignation(Long id,
                                          DesignationRequest request);

    void deleteDesignation(Long id);
	
}
