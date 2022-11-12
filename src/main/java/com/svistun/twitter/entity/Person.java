package com.svistun.twitter.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

import static javax.persistence.FetchType.*;


@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "The name must not be empty")
    @Size(min = 2, max = 100, message = "The username must be between 2 and 100 characters long")
    @Column(nullable = false, unique = true)
    private String username;

    @Email(message = "E-mail should not be empty")
    @Column(unique = true, nullable = false)
    private String email;

    @NonNull
    @NotBlank(message = "New password is mandatory")
    @Column(nullable = false)
    private String password;

    private LocalDateTime registrationDate;

    @ManyToMany(fetch = EAGER)
    private Collection<Role> roles;


}