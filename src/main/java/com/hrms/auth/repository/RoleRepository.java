package com.hrms.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hrms.auth.entity.Role;
import com.hrms.common.enums.RoleName;

public interface RoleRepository extends JpaRepository<Role, Long>{

	 Optional<Role> findByName(RoleName name);

	    boolean existsByName(RoleName name);
	
}
