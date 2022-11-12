package com.svistun.twitter.service.person;

import com.svistun.twitter.dto.PersonDto;
import com.svistun.twitter.dto.RequestToChangeUserRoleDto;
import com.svistun.twitter.dto.ResponseChangeUserRoleDto;
import com.svistun.twitter.dto.RoleDto;
import com.svistun.twitter.entity.Person;
import com.svistun.twitter.entity.RequestToChangeUserRole;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PersonService {
    Person saveUser(PersonDto personDto);

    List<Person> getAll();
    void editPerson(PersonDto personDto, Authentication authentication);
    Person getPerson(String username);

    void addRequestToChangeUserRole(Authentication authentication, RoleDto roleDto);
    List<RequestToChangeUserRole> personRequestRole(Authentication authentication);

}
