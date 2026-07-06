package com.hrms.common.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.hrms.auth.entity.Role;
import com.hrms.auth.repository.RoleRepository;
import com.hrms.common.enums.RoleName;


@Component
public class DataInitializer implements CommandLineRunner {

	private final RoleRepository roleRepository;

    public DataInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) {

        createRole(RoleName.SUPER_ADMIN, "System Super Administrator");
        createRole(RoleName.COMPANY_ADMIN, "Company Administrator");
        createRole(RoleName.HR_MANAGER, "HR Manager");
        createRole(RoleName.MANAGER, "Department Manager");
        createRole(RoleName.EMPLOYEE, "Employee");

    }

    private void createRole(RoleName roleName, String description) {

        if (!roleRepository.existsByName(roleName)) {

            Role role = new Role();
            role.setName(roleName);
            role.setDescription(description);

            roleRepository.save(role);

            System.out.println(roleName + " inserted.");
        }

    }
	
}
