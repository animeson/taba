package com.svistun.twitter.service.role;

import com.svistun.twitter.entity.ERole;
import com.svistun.twitter.entity.Role;
import com.svistun.twitter.repository.RoleRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Log4j2
public class RoleServiceImpl implements RoleService{
    private final RoleRepo roleRepo;
    @Override
    @Transactional
    public void saveRole(ERole role) {
        if (!roleRepo.existsByRoleName(role)) {
            if (role.equals(ERole.ROLE_USER)) {
                roleRepo.save(new Role(null, role));
            } else {
                log.warn("Incorrect role data");
            }
        } else {
            log.warn("We already have such a role.");
        }
    }

    @Override
    public Role findByRoleName(ERole roleName) {
        return roleRepo.findByRoleName(roleName);
    }

    @Override
    public Boolean existsByRoleName(ERole roleName) {
        return roleRepo.existsByRoleName(roleName);
    }
}
