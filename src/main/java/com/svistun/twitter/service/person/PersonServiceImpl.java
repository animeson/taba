package com.svistun.twitter.service.person;

import com.svistun.twitter.dto.PersonDto;
import com.svistun.twitter.dto.RoleDto;
import com.svistun.twitter.entity.ERole;
import com.svistun.twitter.entity.Person;
import com.svistun.twitter.entity.RequestToChangeUserRole;
import com.svistun.twitter.entity.Role;
import com.svistun.twitter.repository.PersonRepo;
import com.svistun.twitter.repository.RequestToChangeUserRoleRepo;
import com.svistun.twitter.service.role.RoleServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.svistun.twitter.entity.ERole.ROLE_ADMIN;
import static com.svistun.twitter.entity.ERole.ROLE_USER;

@Service
@Log4j2
public class PersonServiceImpl implements PersonService, UserDetailsService {
    private final PersonRepo personRepo;
    private final RoleServiceImpl roleService;
    private final RequestToChangeUserRoleRepo requestToChangeUserRoleRepo;
    private final PasswordEncoder passwordEncoder;

    public PersonServiceImpl(PersonRepo personRepo, RoleServiceImpl roleService,
                             RequestToChangeUserRoleRepo requestToChangeUserRoleRepo,
                             @Lazy PasswordEncoder passwordEncoder) {
        this.personRepo = personRepo;
        this.roleService = roleService;
        this.requestToChangeUserRoleRepo = requestToChangeUserRoleRepo;
        this.passwordEncoder = passwordEncoder;
    }


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


    @Override
    @Transactional
    public Person saveUser(PersonDto personDto) {
        if (!roleService.existsByRoleName(ROLE_USER)) {
            roleService.saveRole(ROLE_USER);
        }
        Role role = roleService.findByRoleName(ROLE_USER);
        Collection<Role> roles = new ArrayList<>();
        roles.add(role);
        Person person = new Person();
        BeanUtils.copyProperties(personDto, person);
        person.setRegistrationDate(LocalDateTime.now());
        person.setPassword(passwordEncoder.encode(personDto.getPassword()));
        person.setRoles(roles);
        return personRepo.save(person);
    }

    @Override
    public List<Person> getAll() {
        return personRepo.findAll();
    }

    @Override
    @Transactional
    public void editPerson(PersonDto personDto, Authentication authentication) {
        Person person = personRepo.findByUsername(authentication.getName());
        BeanUtils.copyProperties(personDto, person);
        person.setPassword(passwordEncoder.encode(personDto.getPassword()));
        personRepo.save(person);
    }


    @Override
    public Person getPerson(String username) {
        return personRepo.findByUsername(username);
    }

    @Override
    @Transactional
    public void addRequestToChangeUserRole(Authentication authentication, RoleDto roleDto) {
        if (roleDto.getRoleName().equals(ROLE_USER.name())
                || roleDto.getRoleName().equals(ROLE_ADMIN.name())) {
            RequestToChangeUserRole newRole = new RequestToChangeUserRole();
            newRole.setPerson(personRepo.findByUsername(authentication.getName()));
            newRole.setStatus(false);
            Role role = roleService.findByRoleName(ERole.valueOf(roleDto.getRoleName()));
            newRole.setRole(role);
            requestToChangeUserRoleRepo.save(newRole);
        } else {
            log.error("Role : {} -  does not exist", roleDto.getRoleName());
            throw new RuntimeException();
        }
    }

    @Override
    public List<RequestToChangeUserRole> personRequestRole(Authentication authentication) {
        return requestToChangeUserRoleRepo.findAllByPersonId(personRepo
                .findByUsername(authentication.getName()).getId());
    }

}
