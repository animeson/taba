package com.svistun.twitter.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import javax.persistence.JoinColumn;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreatePersonDto {
    @NotEmpty(message = "The name must not be empty")
    @Size(min = 2, max = 100, message = "The username must be between 2 and 100 characters long")
    private String username;

    @Email(message = "E-mail should not be empty")
    @JoinColumn(unique = true, nullable = false)
    private String email;

    @NonNull
    @NotBlank(message = "New password is mandatory")
    @JoinColumn(nullable = false)
    private LocalDate doBirth;
    private String password;
    private String lastName;
    private String firstName;
    private String phone;
    private String address;
    private boolean gender;
    private String bio;
}
