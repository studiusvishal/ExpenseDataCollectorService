package com.bhavsar.vishal.service.datacollector.security.services.impl;

import com.bhavsar.vishal.service.datacollector.db.entity.user.Role;
import com.bhavsar.vishal.service.datacollector.model.login.ERole;
import com.bhavsar.vishal.service.datacollector.repository.RoleRepository;
import com.bhavsar.vishal.service.datacollector.security.services.RoleService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Log4j2
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Optional<Role> findByName(final ERole roleEnum) {
        return roleRepository.findByName(roleEnum);
    }

    @Override
    public Role save(final Role role) {
        roleRepository.save(role);
        return role;
    }
}
