package com.svistun.twitter.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.svistun.twitter.dto.CreatePersonDto;
import com.svistun.twitter.entity.Person;
import com.svistun.twitter.facade.person.PersonFacadeImpl;
import com.svistun.twitter.filter.PersonAuthenticationFilter;
import com.svistun.twitter.filter.PersonAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/")
@Log4j2
public class AuthController {
    private final PersonFacadeImpl personFacade;

    @PostMapping("/register")
    public void register(@RequestBody CreatePersonDto personDto, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Person person = personFacade.save(personDto);
        List<String> roles = person.getRoles().stream().map(role ->
                role.getRoleName().name()).toList();
        String access_token = PersonAuthenticationFilter.generatorAccessToken(request, person.getUsername(), roles);
        String refresh_token = PersonAuthenticationFilter.generatorRefreshToken(request, person.getUsername(), roles);
        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", access_token);
        tokens.put("refresh_token", refresh_token);
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
        log.info("A new user {} with the role was created",personDto.getUsername());
    }

    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refresh_token = authorizationHeader.substring(7);
                DecodedJWT decodedJWT = PersonAuthorizationFilter.verifyToken(refresh_token);
                Person person = personFacade.findPersonByEmail(decodedJWT.getSubject());
                List<String> roles = person.getRoles().stream().map(role ->
                        role.getRoleName().name()).toList();
                String access_token = PersonAuthenticationFilter.generatorAccessToken(request, person.getEmail(), roles);
                refresh_token = PersonAuthenticationFilter.generatorRefreshToken(request, person.getEmail(), roles);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                tokens.put("refresh_token", refresh_token);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            } catch (Exception exception) {
                throw new RuntimeException("Can't write response");
            }
        } else {
            throw new RuntimeException("Refresh token is missing");
        }
    }

}

