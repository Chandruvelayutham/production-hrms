package com.hrms.test;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class RoleTestController {

	@GetMapping("/api/test/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String admin() {
        return "Welcome ADMIN";
    }

    @GetMapping("/api/test/hr")
    @PreAuthorize("hasRole('HR')")
    public String hr() {
        return "Welcome HR";
    }

    @GetMapping("/api/test/manager")
    @PreAuthorize("hasRole('MANAGER')")
    public String manager() {
        return "Welcome MANAGER";
    }

    @GetMapping("/api/test/employee")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public String employee() {
        return "Welcome EMPLOYEE";
    }
}
