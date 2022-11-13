
package com.svistun.twitter.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.svistun.twitter.dto.PersonLoginDto;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PersonAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final static String secret = System.getenv("jwt.secret");
    private final static String expirationTime = System.getenv("jwt.expiration");

    public PersonAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        PersonLoginDto personLoginDto;
        try {
            personLoginDto = new ObjectMapper().readValue(request.getInputStream(), PersonLoginDto.class);
        } catch (IOException e) {
            throw new UsernameNotFoundException("User notFoundException");
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(personLoginDto.getUsername(), personLoginDto.getPassword());
        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        User user = (User) authResult.getPrincipal();
        List<String> roles = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

        String access_token = generatorAccessToken(request, user.getUsername(), roles);
        String refresh_token = generatorRefreshToken(request, user.getUsername(), roles);

        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", access_token);
        tokens.put("refresh_token", refresh_token);
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
    }


    public static String generatorAccessToken(HttpServletRequest request, String username, List<String> role) {

        return generatorToken(request, username, role, System.currentTimeMillis() + 1000 * Long.parseLong(expirationTime));
    }

    public static String generatorRefreshToken(HttpServletRequest request, String username, List<String> role) {
        return generatorToken(request, username, role, System.currentTimeMillis() + 30 * 1000 * Long.parseLong(expirationTime));
    }

    public static String generatorToken(@NotNull HttpServletRequest request, String username, List<String> roles, Long expiration) {
        Algorithm algorithm = Algorithm.HMAC256(secret.getBytes());

        return JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(expiration))
                .withIssuer(request.getRequestURI())
                .withClaim("roles", roles)
                .sign(algorithm);
    }


}
