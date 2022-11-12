package com.svistun.twitter.controller;

import com.svistun.twitter.dto.PersonDto;
import com.svistun.twitter.dto.RoleDto;
import com.svistun.twitter.entity.Person;
import com.svistun.twitter.service.person.PersonServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/user")
public class PersonController {
    private final PersonServiceImpl personService;

    @GetMapping
    public ResponseEntity<Person> getUser(Authentication authentication) {
        return ResponseEntity.ok().body(personService.getPerson(authentication.getName()));
    }

    @PatchMapping
    public ResponseEntity<Person> editPerson(
            @RequestBody PersonDto personDto, Authentication authentication) {
        personService.editPerson(personDto, authentication);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/role/edit")
    public ResponseEntity<Person> editPersonRole(
            @RequestBody RoleDto roleDto, Authentication authentication) {
        personService.addRequestToChangeUserRole(authentication, roleDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/role")
    public ResponseEntity<?> personRequestRole(Authentication authentication) {
        return ResponseEntity.ok().body(personService.personRequestRole(authentication));
    }

}
