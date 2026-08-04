package com.hrms.dashboard.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttendanceSummary {

	private long present;

    private long absent;

    private long halfDay;

    private long late;

    private long onLeave;
	
}
