package com.svistun.twitter.facade.person;

import com.svistun.twitter.dto.CreatePersonDto;
import com.svistun.twitter.dto.PersonDto;
import com.svistun.twitter.entity.Person;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface PersonFacade {
    List<PersonDto> transactionFromEntityToDTO(List<Person> person);
    PersonDto transactionFromEntityToDTO(Person person);
    PersonDto transactionFromEntityToDTO(String username);
    Person findPersonByEmail(String email);
    Person save(CreatePersonDto createPersonDto);




}
