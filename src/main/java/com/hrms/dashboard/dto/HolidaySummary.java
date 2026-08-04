package com.hrms.dashboard.dto;

import java.time.LocalDate;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HolidaySummary {

    private Long id;

    private String holidayName;

    private LocalDate holidayDate;

    private String description;

    private Boolean optional;	
}
