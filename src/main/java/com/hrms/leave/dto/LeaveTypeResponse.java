package com.hrms.leave.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeaveTypeResponse {

	private Long id;

	private String leaveCode;

	private String leaveName;

	private String description;

	private Integer maxDays;

	private Boolean active;

}
