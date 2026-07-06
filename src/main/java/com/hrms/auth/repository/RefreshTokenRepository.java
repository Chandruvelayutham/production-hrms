package com.hrms.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hrms.auth.entity.RefreshToken;
import com.hrms.auth.entity.User;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long>{

	Optional<RefreshToken> findByToken(String token);

    void deleteByUser(User user);
	
}
