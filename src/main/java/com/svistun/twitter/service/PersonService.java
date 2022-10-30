package com.svistun.twitter.service;

import com.svistun.twitter.dto.RegistrationPersonDto;
import com.svistun.twitter.entity.Person;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PersonService {
    void saveUser(RegistrationPersonDto registrationPersonDto);
    List<Person> getAll();
    Person gerPerson(String username);
}
