package com.svistun.twitter.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class PersonLoginDto {

    @Email
    private String email;

    @Column(nullable = false)
    @NotBlank(message = "The password must not be empty")
    @Size(min = 2, max = 100, message = "The password must be between 2 and 100 characters long")
    private String password;
}
