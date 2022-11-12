package com.svistun.twitter.controller;

import com.svistun.twitter.dto.PostDto;
import com.svistun.twitter.entity.Post;
import com.svistun.twitter.service.post.PostServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/post")
public class PostController {
    private final PostServiceImpl postService;

    @GetMapping("/{username}")
    public ResponseEntity<List<Post>> getPost(@PathVariable String username) {
        return ResponseEntity.ok().body(postService.getPost(username));
    }


    @PostMapping
    public ResponseEntity<Post> addPost(@RequestBody PostDto postDto, Authentication authentication) {
        return ResponseEntity.ok().body(postService.savePost(postDto,authentication));
    }
}
