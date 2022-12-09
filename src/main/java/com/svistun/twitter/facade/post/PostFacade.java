package com.svistun.twitter.facade.post;

import com.svistun.twitter.dto.CreatePostDto;
import com.svistun.twitter.dto.PostDto;
import com.svistun.twitter.entity.Post;
import org.springframework.security.core.Authentication;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface PostFacade {
    List<PostDto> transactionFromEntityToDTO(List<Post> posts, Pageable pageable);

    PostDto transactionFromEntityToDTO(Post post);

    PostDto savePost(CreatePostDto createPostDto, Authentication authentication);

    List<PostDto> findAllPostByUsername(String username, Pageable pageable);

    List<PostDto> findAllPostByEmail(String email, Pageable pageable);
}
