package com.svistun.twitter.repository;

import com.svistun.twitter.entity.ERole;
import com.svistun.twitter.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {
    Role findByRoleName(ERole roleName);
    Boolean existsByRoleName(ERole roleName);

}
