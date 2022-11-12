package com.svistun.twitter.service.post;

import com.svistun.twitter.dto.PostDto;
import com.svistun.twitter.entity.Post;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface PostService {
    Post savePost(PostDto postDto, Authentication authentication);
    List<Post> getPost(String username);
}
