package com.hrms.holiday.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HolidayResponse {

    private Long id;

    private Long companyId;

    private String companyName;

    private String holidayName;

    private LocalDate holidayDate;

    private String description;

    private Boolean optional;

    private Boolean active;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
