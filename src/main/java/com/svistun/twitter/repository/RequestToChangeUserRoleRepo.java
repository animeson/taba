package com.svistun.twitter.repository;

import com.svistun.twitter.entity.RequestToChangeUserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RequestToChangeUserRoleRepo extends JpaRepository<RequestToChangeUserRole, Long> {
    List<RequestToChangeUserRole> findAllByStatus(Boolean status);
    List<RequestToChangeUserRole> findAllByPersonId(Long id);

    RequestToChangeUserRole findByPersonId(Long id);


}
