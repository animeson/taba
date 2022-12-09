package com.svistun.twitter.controller;

import com.svistun.twitter.dto.PersonDto;
import com.svistun.twitter.entity.Person;
import com.svistun.twitter.facade.person.PersonFacadeImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/user")
public class PersonController {
    private final PersonFacadeImpl personFacade;

    @GetMapping
    public ResponseEntity<Person> getUser(Authentication authentication) {
        return ResponseEntity.ok().body(personFacade.findPersonByEmail(authentication.getName()));
    }

    @GetMapping("/{username}")
    public ResponseEntity<PersonDto> getPersonByUsername(@PathVariable String username){
        return ResponseEntity.ok().body(personFacade.transactionFromEntityToDTO(username));
    }

/*    @PatchMapping
    public ResponseEntity<PersonDto> editPerson(
            @RequestBody CreatePersonDto personDto, Authentication authentication) {
        return ResponseEntity.ok().body(personFacade.editPerson(personDto, authentication));
    }*/

}
