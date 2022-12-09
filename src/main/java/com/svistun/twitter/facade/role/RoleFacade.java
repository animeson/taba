package com.svistun.twitter.facade.role;

import com.svistun.twitter.entity.ERole;
import com.svistun.twitter.entity.Role;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public interface RoleFacade {
    Collection<Role> createRole(ERole eRole);
}
