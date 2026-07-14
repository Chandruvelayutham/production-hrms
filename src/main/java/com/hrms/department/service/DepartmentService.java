package com.hrms.department.service;

import java.util.List;

import com.hrms.department.dto.DepartmentRequest;
import com.hrms.department.dto.DepartmentResponse;

public interface DepartmentService {

	DepartmentResponse createDepartment(DepartmentRequest request);

    List<DepartmentResponse> getAllDepartments();

    DepartmentResponse getDepartmentById(Long id);

    DepartmentResponse updateDepartment(Long id, DepartmentRequest request);

    void deleteDepartment(Long id);
	
}
