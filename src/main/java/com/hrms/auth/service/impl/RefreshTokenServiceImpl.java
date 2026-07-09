package com.hrms.auth.service.impl;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.hrms.auth.dto.RefreshTokenRequest;
import com.hrms.auth.dto.RefreshTokenResponse;
import com.hrms.auth.entity.RefreshToken;
import com.hrms.auth.entity.User;
import com.hrms.auth.repository.RefreshTokenRepository;
import com.hrms.auth.security.CustomUserDetails;
import com.hrms.auth.security.JwtService;
import com.hrms.auth.service.RefreshTokenService;
import com.hrms.common.exception.UnauthorizedException;
import org.springframework.transaction.annotation.Transactional;	
@Service
@Transactional
public class RefreshTokenServiceImpl implements RefreshTokenService{

	    private final RefreshTokenRepository refreshTokenRepository;
	    private final JwtService jwtService;

	    public RefreshTokenServiceImpl(
	            RefreshTokenRepository refreshTokenRepository,
	            JwtService jwtService) {

	        this.refreshTokenRepository = refreshTokenRepository;
	        this.jwtService = jwtService;
	    }

	    @Override
	    public RefreshTokenResponse refreshToken(
	            RefreshTokenRequest request) {

	        RefreshToken refreshToken =
	                refreshTokenRepository.findByToken(request.getRefreshToken())
	                        .orElseThrow(() ->
	                                new UnauthorizedException("Invalid refresh token"));

	        if (refreshToken.isRevoked()) {
	            throw new UnauthorizedException("Refresh token revoked");
	        }

	        if (refreshToken.getExpiryDate().isBefore(LocalDateTime.now())) {
	            throw new UnauthorizedException("Refresh token expired");
	        }

	        User user = refreshToken.getUser();

	        CustomUserDetails userDetails =
	                new CustomUserDetails(user);

	        String newAccessToken =
	                jwtService.generateAccessToken(userDetails);

	        return RefreshTokenResponse.builder()
	                .accessToken(newAccessToken)
	                .refreshToken(refreshToken.getToken())
	                .tokenType("Bearer")
	                .expiresIn(900)
	                .build();
	    }
	    
	    
	    @Override
	    public void revokeUserTokens(User user) {

	        refreshTokenRepository.deleteByUser(user);

	    }
	    
	    @Override
	    public RefreshToken createRefreshToken(User user, String token) {

	        RefreshToken refreshToken = new RefreshToken();

	        refreshToken.setUser(user);
	        refreshToken.setToken(token);
	        refreshToken.setRevoked(false);
	        refreshToken.setExpiryDate(LocalDateTime.now().plusDays(7));

	        return refreshTokenRepository.save(refreshToken);

	    }
	    
	    @Override
	    public void logout(String token) {

	        RefreshToken refreshToken = refreshTokenRepository
	                .findByToken(token)
	                .orElseThrow(() ->
	                        new UnauthorizedException("Invalid refresh token"));

	        refreshToken.setRevoked(true);

	        refreshTokenRepository.save(refreshToken);

	    }
	
}
