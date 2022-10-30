package com.svistun.twitter.controller;

import com.svistun.twitter.dto.RegistrationPersonDto;
import com.svistun.twitter.entity.Person;
import com.svistun.twitter.service.PersonServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/user")
public class PersonController {
    private final PersonServiceImpl personService;

    @GetMapping
    public ResponseEntity<List<Person>> getUser() {
        return ResponseEntity.ok().body(personService.getAll());
    }

    @PostMapping
    public ResponseEntity<List<Person>> saveUser(
            @RequestBody RegistrationPersonDto registrationPersonDto) {

        personService.saveUser(registrationPersonDto);
        return ResponseEntity.ok().build();
    }
}
