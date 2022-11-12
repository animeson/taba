package com.svistun.twitter.service.admin;

import com.svistun.twitter.dto.ResponseChangeUserRoleDto;
import com.svistun.twitter.entity.ERole;
import com.svistun.twitter.entity.Person;
import com.svistun.twitter.entity.RequestToChangeUserRole;
import com.svistun.twitter.entity.Role;
import com.svistun.twitter.repository.PersonRepo;
import com.svistun.twitter.repository.RequestToChangeUserRoleRepo;
import com.svistun.twitter.service.role.RoleServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class AdminServiceImp implements AdminService {
    private final RequestToChangeUserRoleRepo requestToChangeUserRoleRepo;
    private final PersonRepo personRepo;
    private final RoleServiceImpl roleService;

    @Override
    @Transactional
    public void editRolePerson(ResponseChangeUserRoleDto responseChangeUserRoleDto, Long id) {
        if (responseChangeUserRoleDto.getStatus().equals("true")
                || responseChangeUserRoleDto.getStatus().equals("false")) {
            RequestToChangeUserRole requestToChangeUserRole = requestToChangeUserRoleRepo.findByPersonId(id);
            Role role = roleService.findByRoleName(requestToChangeUserRole.getRole().getRoleName());
            Person person = personRepo.getById(id);
            Collection<Role> roles = new ArrayList<>();
            roles.add(role);
            person.setRoles(roles);
            requestToChangeUserRole.setStatus(Boolean.valueOf(responseChangeUserRoleDto.getStatus()));
            requestToChangeUserRoleRepo.save(requestToChangeUserRole);

        }


    }

    @Override
    public List<RequestToChangeUserRole> getAllRequestByStatus(String status) {
        if (status.equals("")) {
            return requestToChangeUserRoleRepo.findAll();
        } else if (status.equals("true") || status.equals("false")) {
            return requestToChangeUserRoleRepo.findAllByStatus(Boolean.valueOf(status));
        } else {
            return null;
        }
    }
}
