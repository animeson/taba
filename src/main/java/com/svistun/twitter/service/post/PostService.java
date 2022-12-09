package com.svistun.twitter.service.post;

import com.svistun.twitter.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public interface PostService {
    void savePost(Post post);

    Page<Post> findAllPostByUsername(String username, Pageable pageable);
    Page<Post> findAllPostByEmail(String email, Pageable pageable);
    void deletePostById(Long id, Authentication authentication);
}
