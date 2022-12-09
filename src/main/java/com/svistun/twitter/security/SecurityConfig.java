package com.svistun.twitter.security;

import com.svistun.twitter.filter.PersonAuthenticationFilter;
import com.svistun.twitter.filter.PersonAuthorizationFilter;
import com.svistun.twitter.service.person.PersonServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final AuthenticationConfiguration authenticationConfiguration;
    private final PasswordEncoder encoder;

    public SecurityConfig(AuthenticationConfiguration authenticationConfiguration,
                          AuthenticationManagerBuilder auth,
                          PersonServiceImpl personService) throws Exception {
        this.authenticationConfiguration = authenticationConfiguration;
        this.encoder = new BCryptPasswordEncoder();

        auth.userDetailsService(personService).passwordEncoder(encoder);
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        PersonAuthenticationFilter personAuthenticationFilter =
                new PersonAuthenticationFilter(authenticationConfiguration.getAuthenticationManager());

        personAuthenticationFilter.setFilterProcessesUrl("/api/v1/login");
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests((auth) -> {
                            try {
                                auth
                                        .antMatchers("/api/v1/login/**", "/api/v1/register/**", "/api/v1/token/refresh/**")
                                        .permitAll()
                                        .and()
                                        .authorizeRequests()
                                        .antMatchers("api/v1/user/**","/api/v1/post/**")
                                        .hasAnyAuthority("ROLE_USER","ROLE_ADMIN")
                                        .antMatchers( "/api/v1/admin/**").hasAnyAuthority("ROLE_ADMIN")
                                        .anyRequest()
                                        .authenticated()
                                        .and()
                                        .cors()
                                        .and()
                                        .addFilter(personAuthenticationFilter)
                                        .addFilterBefore(new PersonAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }
                )
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return encoder;
    }

}



