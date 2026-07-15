package com.hrms.designation.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DesignationResponse {

	private Long id;

    private String designationCode;

    private String designationName;

    private String description;

    private Long companyId;

    private String companyName;

    private Boolean active;
}
