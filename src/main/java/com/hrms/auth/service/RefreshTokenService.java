package com.hrms.auth.service;

import com.hrms.auth.dto.RefreshTokenRequest;
import com.hrms.auth.dto.RefreshTokenResponse;
import com.hrms.auth.entity.RefreshToken;
import com.hrms.auth.entity.User;

public interface RefreshTokenService {

	RefreshTokenResponse refreshToken(RefreshTokenRequest request);
	
	RefreshToken createRefreshToken(User user, String token);

    void revokeUserTokens(User user);
    void logout(String refreshToken);
}
