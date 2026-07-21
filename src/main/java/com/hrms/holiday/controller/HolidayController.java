package com.hrms.holiday.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hrms.common.response.ApiResponse;
import com.hrms.holiday.dto.HolidayRequest;
import com.hrms.holiday.dto.HolidayResponse;
import com.hrms.holiday.service.HolidayService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/holidays")
@RequiredArgsConstructor
public class HolidayController {

	private final HolidayService holidayService;

	@PostMapping
    public ResponseEntity<ApiResponse<HolidayResponse>>
            createHoliday(
                    @Valid @RequestBody
                    HolidayRequest request) {

        HolidayResponse response =
                holidayService.createHoliday(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        ApiResponse.success(
                                "Holiday created successfully",
                                response));
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<ApiResponse<List<HolidayResponse>>>
            getAllHolidays(
                    @PathVariable Long companyId) {

        List<HolidayResponse> response =
                holidayService
                        .getAllHolidays(companyId);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Holidays retrieved successfully",
                        response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<HolidayResponse>>
            getHolidayById(
                    @PathVariable Long id) {

        HolidayResponse response =
                holidayService
                        .getHolidayById(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Holiday retrieved successfully",
                        response));
    }

    @GetMapping("/company/{companyId}/between")
    public ResponseEntity<ApiResponse<List<HolidayResponse>>>
            getHolidaysBetweenDates(
                    @PathVariable Long companyId,
                    @RequestParam LocalDate startDate,
                    @RequestParam LocalDate endDate) {

        List<HolidayResponse> response =
                holidayService
                        .getHolidaysBetweenDates(
                                companyId,
                                startDate,
                                endDate);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Holidays retrieved successfully",
                        response));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<HolidayResponse>>
            updateHoliday(
                    @PathVariable Long id,
                    @Valid @RequestBody
                    HolidayRequest request) {

        HolidayResponse response =
                holidayService.updateHoliday(
                        id,
                        request);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Holiday updated successfully",
                        response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>>
            deactivateHoliday(
                    @PathVariable Long id) {

        holidayService.deactivateHoliday(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Holiday deactivated successfully",
                        null));
    }
	
}
