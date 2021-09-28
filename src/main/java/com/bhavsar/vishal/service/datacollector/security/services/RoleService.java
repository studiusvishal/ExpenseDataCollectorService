package com.bhavsar.vishal.service.datacollector.security.services;

import com.bhavsar.vishal.service.datacollector.db.entity.user.Role;
import com.bhavsar.vishal.service.datacollector.model.login.ERole;

import java.util.Optional;

public interface RoleService {
    Optional<Role> findByName(ERole roleEnum);
    Role save(final Role role);
}
