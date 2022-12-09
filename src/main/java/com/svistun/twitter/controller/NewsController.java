package com.svistun.twitter.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/news")
public class NewsController {


    //TODO создать фасад "новости" и чекать есть ли друзья если есть,
    // выводить свежие новости по дате примерно за сутки, если друзей нет,
    // то выводить новости которые могут быть и по дате и по месту где живёт чел и т.д

    @GetMapping
    public ResponseEntity<?> getNews(){
        return ResponseEntity.ok().body("");
    }


}
