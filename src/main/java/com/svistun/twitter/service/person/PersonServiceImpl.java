package com.svistun.twitter.service.person;

import com.svistun.twitter.entity.Person;
import com.svistun.twitter.repository.PersonRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;

@Service
@Log4j2
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService, UserDetailsService {
    private final PersonRepo personRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Person person = personRepo.findByEmail(email);
        if (person == null) {
            throw new UsernameNotFoundException("User not found exception");
        }
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        person.getRoles().forEach(role ->
                authorities.add(new SimpleGrantedAuthority(role.getRoleName().name())));
        return new User(person.getEmail(), person.getPassword(), authorities);
    }


    @Override
    @Transactional
    public Person saveUser(Person person) {
        return personRepo.save(person);
    }


    @Override
    public Person findPersonByEmail(String email) {
        return personRepo.findByEmail(email);
    }

    @Override
    public Person getPersonByUsername(String username) {
        return personRepo.findByUsername(username);
    }


}
