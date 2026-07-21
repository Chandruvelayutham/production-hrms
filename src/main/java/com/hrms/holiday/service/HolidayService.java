package com.hrms.holiday.service;

import java.time.LocalDate;
import java.util.List;

import com.hrms.holiday.dto.HolidayRequest;
import com.hrms.holiday.dto.HolidayResponse;

public interface HolidayService {

	HolidayResponse createHoliday(HolidayRequest request);

	List<HolidayResponse> getAllHolidays(Long companyId);

	HolidayResponse getHolidayById(Long id);

	List<HolidayResponse> getHolidaysBetweenDates(Long companyId, LocalDate startDate, LocalDate endDate);

	HolidayResponse updateHoliday(Long id, HolidayRequest request);

	void deactivateHoliday(Long id);

}
