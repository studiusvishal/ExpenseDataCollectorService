package com.bhavsar.vishal.service.datacollector.config;

import com.bhavsar.vishal.service.datacollector.model.login.ERole;
import com.bhavsar.vishal.service.datacollector.model.login.Role;
import com.bhavsar.vishal.service.datacollector.security.services.RoleService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Log4j2
@Component
public class RoleLoader implements ApplicationRunner {
    boolean isSetup = false;

    @Autowired
    private RoleService roleService;

    @Override
    public void run(final ApplicationArguments args) {
        if (isSetup) {
            return;
        }
        Arrays.stream(ERole.values()).forEach(this::loadRolesInDB);
        isSetup = true;
    }

    private Role loadRolesInDB(final ERole eRole) {
        final var roleByName = roleService.findByName(eRole);
        if (roleByName.isPresent()) {
            log.info("Role [{}] is already loaded in db. Won't load again.", eRole.name());
            return null;
        }
        final var role = Role.builder().name(eRole).build();
        log.debug("Loading role [{}] in db.", eRole.name());
        return roleService.save(role);
    }
}
