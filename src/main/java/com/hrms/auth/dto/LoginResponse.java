package com.hrms.auth.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse {

	private String accessToken;

	private String refreshToken;

	private String tokenType;

	private long expiresIn;
}
