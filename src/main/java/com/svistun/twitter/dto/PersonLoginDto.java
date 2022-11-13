package com.svistun.twitter.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class PersonLoginDto {

    @NotBlank(message = "The name must not be empty")
    @Size(min = 2, max = 100, message = "The username must be between 2 and 100 characters long")
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    @NotBlank(message = "The password must not be empty")
    @Size(min = 2, max = 100, message = "The password must be between 2 and 100 characters long")
    private String password;
}
