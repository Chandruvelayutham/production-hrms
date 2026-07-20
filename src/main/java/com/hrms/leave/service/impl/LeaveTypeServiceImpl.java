package com.hrms.leave.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hrms.leave.dto.LeaveTypeRequest;
import com.hrms.leave.dto.LeaveTypeResponse;
import com.hrms.leave.repository.LeaveTypeRepository;
import com.hrms.leave.service.LeaveTypeService;
import com.hrms.common.exception.DuplicateResourceException;
import com.hrms.common.exception.ResourceNotFoundException;
import com.hrms.leave.entity.LeaveType;
@Service
public class LeaveTypeServiceImpl implements LeaveTypeService{

	private final LeaveTypeRepository leaveTypeRepository;

    public LeaveTypeServiceImpl(
            LeaveTypeRepository leaveTypeRepository) {

        this.leaveTypeRepository = leaveTypeRepository;
    }

    @Override
    public LeaveTypeResponse createLeaveType(
            LeaveTypeRequest request) {

        if (leaveTypeRepository.existsByLeaveCode(
                request.getLeaveCode())) {

            throw new DuplicateResourceException(
                    "Leave code already exists.");
        }

        if (leaveTypeRepository.existsByLeaveName(
                request.getLeaveName())) {

            throw new DuplicateResourceException(
                    "Leave name already exists.");
        }

        LeaveType leaveType = LeaveType.builder()
                .leaveCode(request.getLeaveCode())
                .leaveName(request.getLeaveName())
                .description(request.getDescription())
                .maxDays(request.getMaxDays())
                .active(true)
                .build();

        LeaveType savedLeaveType =
                leaveTypeRepository.save(leaveType);

        return mapToResponse(savedLeaveType);
    }

    @Override
    public List<LeaveTypeResponse> getAllLeaveTypes() {

        return leaveTypeRepository.findByActiveTrue()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public LeaveTypeResponse getLeaveTypeById(Long id) {

        LeaveType leaveType = leaveTypeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Leave type not found with id: " + id));

        return mapToResponse(leaveType);
    }

    @Override
    public LeaveTypeResponse updateLeaveType(
            Long id,
            LeaveTypeRequest request) {

        LeaveType leaveType = leaveTypeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Leave type not found with id: " + id));

        if (!leaveType.getLeaveCode().equals(request.getLeaveCode())
                && leaveTypeRepository.existsByLeaveCode(
                        request.getLeaveCode())) {

            throw new DuplicateResourceException(
                    "Leave code already exists.");
        }

        if (!leaveType.getLeaveName().equals(request.getLeaveName())
                && leaveTypeRepository.existsByLeaveName(
                        request.getLeaveName())) {

            throw new DuplicateResourceException(
                    "Leave name already exists.");
        }

        leaveType.setLeaveCode(request.getLeaveCode());
        leaveType.setLeaveName(request.getLeaveName());
        leaveType.setDescription(request.getDescription());
        leaveType.setMaxDays(request.getMaxDays());

        LeaveType updatedLeaveType =
                leaveTypeRepository.save(leaveType);

        return mapToResponse(updatedLeaveType);
    }

    @Override
    public void deleteLeaveType(Long id) {

        LeaveType leaveType = leaveTypeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Leave type not found with id: " + id));

        leaveType.setActive(false);

        leaveTypeRepository.save(leaveType);
    }
	
    private LeaveTypeResponse mapToResponse(
            LeaveType leaveType) {

        return LeaveTypeResponse.builder()
                .id(leaveType.getId())
                .leaveCode(leaveType.getLeaveCode())
                .leaveName(leaveType.getLeaveName())
                .description(leaveType.getDescription())
                .maxDays(leaveType.getMaxDays())
                .active(leaveType.getActive())
                .build();
    }
}
