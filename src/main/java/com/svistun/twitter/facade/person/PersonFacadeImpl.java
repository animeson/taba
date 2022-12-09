package com.svistun.twitter.facade.person;

import com.svistun.twitter.dto.CreatePersonDto;
import com.svistun.twitter.dto.PersonDto;
import com.svistun.twitter.entity.ERole;
import com.svistun.twitter.entity.Person;
import com.svistun.twitter.facade.role.RoleFacadeImpl;
import com.svistun.twitter.service.person.PersonService;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component

public class PersonFacadeImpl implements PersonFacade {
    private final PersonService personService;
    private final RoleFacadeImpl roleFacade;
    private final PasswordEncoder passwordEncoder;

    public PersonFacadeImpl(PersonService personService, RoleFacadeImpl roleFacade,
                            @Lazy PasswordEncoder passwordEncoder) {
        this.personService = personService;
        this.roleFacade = roleFacade;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<PersonDto> transactionFromEntityToDTO(List<Person> persons) {
        List<PersonDto> personsDto = new ArrayList<>();
        for (Person person: persons){
            PersonDto personDto = new PersonDto();
            BeanUtils.copyProperties(person,personDto);
            personsDto.add(personDto);
        }
        return personsDto;
    }

    @Override
    public PersonDto transactionFromEntityToDTO(Person person) {
        PersonDto personDto = new PersonDto();
        BeanUtils.copyProperties(person, personDto);
        return personDto;
    }

    @Override
    public PersonDto transactionFromEntityToDTO(String username) {
        Person person = personService.getPersonByUsername(username);
        return transactionFromEntityToDTO(person);
    }

    @Override
    public Person findPersonByEmail(String email) {
        return personService.findPersonByEmail(email);
    }

    @Override
    public Person save(CreatePersonDto createPersonDto) {
        Person person = new Person();
        BeanUtils.copyProperties(createPersonDto, person);
        person.setDoRegistration(LocalDateTime.now());
        person.setPassword(passwordEncoder.encode(createPersonDto.getPassword()));
        person.setRoles(roleFacade.createRole(ERole.ROLE_USER));
        return personService.saveUser(person);
    }


}
