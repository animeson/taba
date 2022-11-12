package com.svistun.twitter.service.admin;

import com.svistun.twitter.dto.ResponseChangeUserRoleDto;
import com.svistun.twitter.entity.RequestToChangeUserRole;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface AdminService {

    void editRolePerson(ResponseChangeUserRoleDto responseChangeUserRoleDto, Long id);
    List<RequestToChangeUserRole> getAllRequestByStatus(String status);

}
