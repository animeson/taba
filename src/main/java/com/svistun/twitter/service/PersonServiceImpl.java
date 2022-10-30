package com.svistun.twitter.service;

import com.svistun.twitter.dto.RegistrationPersonDto;
import com.svistun.twitter.entity.ERole;
import com.svistun.twitter.entity.Person;
import com.svistun.twitter.entity.Role;
import com.svistun.twitter.repository.PersonRepo;
import com.svistun.twitter.repository.RoleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PersonServiceImpl implements PersonService {
    private final PersonRepo personRepo;
    private final RoleRepo roleRepo;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    @Transactional
    public void saveUser(RegistrationPersonDto registrationPersonDto) {
        Role role = new Role();
        role.setRoleName(ERole.ROLE_ADMIN);
        List<Role> roles = new ArrayList<>();
        roles.add(role);

        Person person = new Person();
        BeanUtils.copyProperties(registrationPersonDto, person);
        person.setPassword(bCryptPasswordEncoder.encode(person.getPassword()));
        person.setRoles(roles);

        roleRepo.save(role);
        personRepo.save(person);
    }

    @Override
    public List<Person> getAll() {
        return personRepo.findAll();
    }

    @Override
    public Person gerPerson(String username) {
        return personRepo.findByUsername(username);
    }

}
