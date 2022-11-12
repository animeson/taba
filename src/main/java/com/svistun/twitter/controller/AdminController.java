package com.svistun.twitter.controller;

import com.svistun.twitter.dto.RequestToChangeUserRoleDto;
import com.svistun.twitter.dto.ResponseChangeUserRoleDto;
import com.svistun.twitter.entity.RequestToChangeUserRole;
import com.svistun.twitter.service.admin.AdminServiceImp;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/admin")
public class AdminController {
    private final AdminServiceImp adminService;


    /*findRequestToChangeUserRole if you send it true or false,
      it will display a list of confirmed or pending requests,
      if you send an empty string, it will display all requests */
    @GetMapping("/request/role")
    public ResponseEntity<List<RequestToChangeUserRole>> findRequestToChangeUserRole(
            @RequestBody RequestToChangeUserRoleDto status) {
        return ResponseEntity.ok().body(adminService
                .getAllRequestByStatus(status.getStatus()));
    }

    @PatchMapping("/request/role/{id}")
    public ResponseEntity<?> requestToChangeUserRole(
            @RequestBody ResponseChangeUserRoleDto responseChangeUserRoleDto,
            @PathVariable Long id) {
        adminService.editRolePerson(responseChangeUserRoleDto, id);
        return ResponseEntity.ok().build();
    }

}
