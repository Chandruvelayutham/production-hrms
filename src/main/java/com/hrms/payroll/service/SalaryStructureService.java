package com.hrms.payroll.service;

import com.hrms.payroll.dto.SalaryStructureRequest;
import com.hrms.payroll.dto.SalaryStructureResponse;

public interface SalaryStructureService {

	SalaryStructureResponse createSalaryStructure(SalaryStructureRequest request);

	SalaryStructureResponse getSalaryStructure(Long employeeId);

	SalaryStructureResponse updateSalaryStructure(Long employeeId, SalaryStructureRequest request);
}
