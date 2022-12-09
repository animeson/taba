package com.svistun.twitter.controller;

import com.svistun.twitter.dto.CreatePostDto;
import com.svistun.twitter.dto.PostDto;
import com.svistun.twitter.facade.post.PostFacadeImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/post")
public class PostController {
    private final PostFacadeImpl postFacade;




    @GetMapping("/{username}")
    public ResponseEntity<List<PostDto>> getPost(@PathVariable String username,
                                                 @PageableDefault(size = 5)
                                                 @SortDefault(sort = "creationTime", direction = Sort.Direction.DESC)
                                                 Pageable pageable) {
        return ResponseEntity.ok().body(postFacade.findAllPostByUsername(username,pageable));
    }

    @GetMapping
    public ResponseEntity<List<PostDto>> myPost(Authentication authentication,
                                                @PageableDefault(size = 5)
                                                @SortDefault(sort = "creationTime", direction = Sort.Direction.DESC)
                                                Pageable pageable) {
        return ResponseEntity.ok().body(postFacade.findAllPostByEmail(authentication.getName(), pageable));
    }



    @PostMapping
    public ResponseEntity<PostDto> addPost(@RequestBody CreatePostDto postDto, Authentication authentication) {
        return ResponseEntity.ok().body(postFacade.savePost(postDto, authentication));
    }


/*
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePostById(@PathVariable Long id, Authentication authentication) {
        postFacade.deletePostById(id, authentication);
        return ResponseEntity.ok().build();
    }*/

}
