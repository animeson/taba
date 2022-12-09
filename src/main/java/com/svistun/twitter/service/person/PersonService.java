package com.svistun.twitter.service.person;

import com.svistun.twitter.entity.Person;
import org.springframework.stereotype.Service;

@Service
public interface PersonService {
    Person saveUser(Person person);
    Person findPersonByEmail(String email);
    Person getPersonByUsername(String username);

}
