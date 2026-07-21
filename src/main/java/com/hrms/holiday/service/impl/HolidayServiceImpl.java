package com.hrms.holiday.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.hrms.company.entity.Company;
import com.hrms.company.repository.CompanyRepository;
import com.hrms.common.exception.DuplicateResourceException;
import com.hrms.common.exception.ResourceNotFoundException;
import com.hrms.holiday.dto.HolidayRequest;
import com.hrms.holiday.dto.HolidayResponse;
import com.hrms.holiday.entity.Holiday;
import com.hrms.holiday.repository.HolidayRepository;
import com.hrms.holiday.service.HolidayService;

@Service
@RequiredArgsConstructor
@Transactional
public class HolidayServiceImpl implements HolidayService{

	private final HolidayRepository holidayRepository;

    private final CompanyRepository companyRepository;

    @Override
    public HolidayResponse createHoliday(
            HolidayRequest request) {

        Company company =
                companyRepository
                        .findById(request.getCompanyId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Company not found with id: "
                                        + request.getCompanyId()));

        if (holidayRepository
                .existsByCompanyAndHolidayDate(
                        company,
                        request.getHolidayDate())) {

            throw new DuplicateResourceException(
                    "Holiday already exists for this company on this date.");
        }

        Holiday holiday =
                Holiday.builder()
                        .company(company)
                        .holidayName(
                                request.getHolidayName())
                        .holidayDate(
                                request.getHolidayDate())
                        .description(
                                request.getDescription())
                        .optional(
                                request.getOptional())
                        .active(true)
                        .build();

        Holiday savedHoliday =
                holidayRepository.save(holiday);

        return mapToResponse(savedHoliday);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HolidayResponse> getAllHolidays(
            Long companyId) {

        Company company =
                companyRepository
                        .findById(companyId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Company not found with id: "
                                        + companyId));

        return holidayRepository
                .findByCompanyAndActiveTrue(company)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public HolidayResponse getHolidayById(
            Long id) {

        Holiday holiday =
                holidayRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Holiday not found with id: "
                                        + id));

        return mapToResponse(holiday);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HolidayResponse>
            getHolidaysBetweenDates(
                    Long companyId,
                    LocalDate startDate,
                    LocalDate endDate) {

        Company company =
                companyRepository
                        .findById(companyId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Company not found with id: "
                                        + companyId));

        return holidayRepository
                .findByCompanyAndHolidayDateBetweenAndActiveTrue(
                        company,
                        startDate,
                        endDate)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public HolidayResponse updateHoliday(
            Long id,
            HolidayRequest request) {

        Holiday holiday =
                holidayRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Holiday not found with id: "
                                        + id));

        Company company =
                companyRepository
                        .findById(request.getCompanyId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Company not found with id: "
                                        + request.getCompanyId()));

        boolean dateChanged =
                !holiday.getHolidayDate()
                        .equals(request.getHolidayDate())
                || !holiday.getCompany()
                        .getId()
                        .equals(request.getCompanyId());

        if (dateChanged &&
                holidayRepository
                    .existsByCompanyAndHolidayDate(
                            company,
                            request.getHolidayDate())) {

            throw new DuplicateResourceException(
                    "Holiday already exists for this company on this date.");
        }

        holiday.setCompany(company);
        holiday.setHolidayName(
                request.getHolidayName());
        holiday.setHolidayDate(
                request.getHolidayDate());
        holiday.setDescription(
                request.getDescription());
        holiday.setOptional(
                request.getOptional());

        return mapToResponse(
                holidayRepository.save(holiday));
    }

    @Override
    public void deactivateHoliday(
            Long id) {

        Holiday holiday =
                holidayRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Holiday not found with id: "
                                        + id));

        holiday.setActive(false);

        holidayRepository.save(holiday);
    }

    private HolidayResponse mapToResponse(
            Holiday holiday) {

        return HolidayResponse.builder()
                .id(holiday.getId())
                .companyId(
                        holiday.getCompany()
                                .getId())
                .companyName(
                        holiday.getCompany()
                                .getCompanyName())
                .holidayName(
                        holiday.getHolidayName())
                .holidayDate(
                        holiday.getHolidayDate())
                .description(
                        holiday.getDescription())
                .optional(
                        holiday.getOptional())
                .active(
                        holiday.getActive())
                .createdAt(
                        holiday.getCreatedAt())
                .updatedAt(
                        holiday.getUpdatedAt())
                .build();
    }
	
}
