package com.bhavsar.vishal.service.datacollector.security.services;

import com.bhavsar.vishal.service.datacollector.model.login.ERole;
import com.bhavsar.vishal.service.datacollector.model.login.Role;

import java.util.Optional;

public interface RoleService {
    Optional<Role> findByName(ERole roleEnum);
    Role save(final Role role);
}
