package com.hrms.auth.service;

import java.util.List;

import com.hrms.auth.entity.Role;
import com.hrms.common.enums.RoleName;

public interface RoleService {

	
	Role findByName(RoleName roleName);

    List<Role> findAllRoles();
}
