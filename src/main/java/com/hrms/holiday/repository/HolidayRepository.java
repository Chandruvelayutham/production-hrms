package com.hrms.holiday.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hrms.company.entity.Company;
import com.hrms.holiday.entity.Holiday;

public interface HolidayRepository extends JpaRepository<Holiday, Long> {

	List<Holiday> findByCompanyAndActiveTrue(Company company);

	List<Holiday> findByCompanyAndHolidayDateBetweenAndActiveTrue(Company company, LocalDate startDate,
			LocalDate endDate);

	Optional<Holiday> findByCompanyAndHolidayDate(Company company, LocalDate holidayDate);

	boolean existsByCompanyAndHolidayDate(Company company, LocalDate holidayDate);

}
