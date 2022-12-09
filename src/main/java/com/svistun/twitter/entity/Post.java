package com.svistun.twitter.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 50)
    private String title;

    @Size(max = 255)
    private String destruction;

    private LocalDateTime creationTime;

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "person_id")
    private Person person;

    @OneToMany(fetch = LAZY, cascade = CascadeType.ALL)
    private List<Comment> comments;
}
