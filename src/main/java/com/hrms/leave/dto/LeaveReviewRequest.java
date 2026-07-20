package com.hrms.leave.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeaveReviewRequest {

	@NotNull(message = "Approval status is required")
    private Boolean approved;

    @Size(max = 500)
    private String remarks;
}
