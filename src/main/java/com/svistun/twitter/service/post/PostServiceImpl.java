package com.svistun.twitter.service.post;

import com.svistun.twitter.entity.Post;
import com.svistun.twitter.repository.PostRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {
   private final PostRepo postRepo;

    @Override
    public void savePost(Post post) {
        postRepo.save(post);
    }

    @Override
    public Page<Post> findAllPostByUsername(String username, Pageable pageable) {
        return postRepo.findByPersonUsername(username,pageable);
    }

    @Override
    public Page<Post> findAllPostByEmail(String email, Pageable pageable) {
        return postRepo.findByPersonEmail(email,pageable);
    }

    @Override
    public void deletePostById(Long id, Authentication authentication) {

    }
}
