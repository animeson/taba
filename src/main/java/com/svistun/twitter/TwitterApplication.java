package com.svistun.twitter;

import com.svistun.twitter.dto.PersonDto;
import com.svistun.twitter.entity.ERole;
import com.svistun.twitter.entity.Role;
import com.svistun.twitter.repository.RoleRepo;
import com.svistun.twitter.service.person.PersonService;
import com.svistun.twitter.service.role.RoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class TwitterApplication {

    public static void main(String[] args) {
        SpringApplication.run(TwitterApplication.class, args);
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

/*    @Bean
    CommandLineRunner run(RoleService roleService) {




    }*/


}
