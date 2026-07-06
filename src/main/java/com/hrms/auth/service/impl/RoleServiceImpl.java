package com.hrms.auth.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hrms.auth.entity.Role;
import com.hrms.auth.repository.RoleRepository;
import com.hrms.auth.service.RoleService;
import com.hrms.common.enums.RoleName;
import com.hrms.common.exception.ResourceNotFoundException;

@Service
public class RoleServiceImpl implements RoleService {

	 private final RoleRepository roleRepository;

	    public RoleServiceImpl(RoleRepository roleRepository) {
	        this.roleRepository = roleRepository;
	    }

	    @Override
	    public Role findByName(RoleName roleName) {
	        return roleRepository.findByName(roleName)
	                .orElseThrow(() -> new ResourceNotFoundException("Role not found : " + roleName));
	    }

	    @Override
	    public List<Role> findAllRoles() {
	        return roleRepository.findAll();
	    }
	
}
