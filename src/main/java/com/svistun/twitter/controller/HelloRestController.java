package com.svistun.twitter.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api")
public class HelloRestController {


    @GetMapping("/users")
    public String helloUser() {
        return "user";
    }

    @GetMapping("/admins")
    public String helloAdmin() {
        return "admin";
    }

}