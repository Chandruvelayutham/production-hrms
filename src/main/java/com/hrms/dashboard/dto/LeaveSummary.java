package com.hrms.dashboard.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeaveSummary {

	private long pending;

	private long approved;

	private long rejected;

	private long cancelled;
}
