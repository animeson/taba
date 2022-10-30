package com.svistun.twitter.repository;

import com.svistun.twitter.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface PersonRepo extends JpaRepository<Person, Long> {
    Person findByUsername(String username);
}
