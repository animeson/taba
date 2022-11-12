package com.svistun.twitter.service.person;

import com.svistun.twitter.entity.Person;
import com.svistun.twitter.repository.PersonRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;


@Service
@RequiredArgsConstructor
public class PersonDetailsService implements UserDetailsService {
    private final PersonRepo personRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person person = personRepo.findByUsername(username);
        if (person == null) {
            throw new UsernameNotFoundException("User not found exception");
        }
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        person.getRoles().forEach(role ->
                authorities.add(new SimpleGrantedAuthority(role.getRoleName().name())));
        return new User(person.getUsername(), person.getPassword(), authorities);
    }

}
