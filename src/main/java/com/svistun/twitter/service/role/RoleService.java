package com.svistun.twitter.service.role;

import com.svistun.twitter.entity.ERole;
import com.svistun.twitter.entity.Role;
import org.springframework.stereotype.Service;

@Service
public interface RoleService {
    Role saveRole(Role role);
    Role findByRoleName(ERole roleName);
    Boolean existsByRoleName(ERole roleName);

}
