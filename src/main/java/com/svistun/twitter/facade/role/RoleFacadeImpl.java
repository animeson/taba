package com.svistun.twitter.facade.role;

import com.svistun.twitter.entity.ERole;
import com.svistun.twitter.entity.Role;
import com.svistun.twitter.service.role.RoleServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
@RequiredArgsConstructor
public class RoleFacadeImpl implements RoleFacade {
    private final RoleServiceImpl roleService;

    @Override
    public Collection<Role> createRole(ERole eRole) {
        Role role = new Role();
        role.setRoleName(eRole);
        Collection<Role> roles = new ArrayList<>();

        if (!roleService.existsByRoleName(eRole)) {
            roleService.saveRole(role);
        } else {
            role = roleService.findByRoleName(eRole);
        }
        roles.add(role);
        return roles;
    }

}
