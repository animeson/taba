package com.svistun.twitter.dto;

import com.svistun.twitter.entity.Role;
import lombok.Data;

import javax.persistence.JoinColumn;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
@Data
public class PersonResponseDto {

    @NotEmpty(message = "The name must not be empty")
    @Size(min = 2, max = 100, message = "The username must be between 2 and 100 characters long")
    private String username;

    @Email(message = "E-mail should not be empty")
    @JoinColumn(unique = true, nullable = false)
    private String email;

    private RoleDto role;
}
