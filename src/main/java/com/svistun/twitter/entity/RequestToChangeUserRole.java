package com.svistun.twitter.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.Collection;

import static javax.persistence.FetchType.EAGER;

@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RequestToChangeUserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "person_id")
    private Person person;

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "role_id")
    private Role role;

    private Boolean status;
}
