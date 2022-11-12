package com.svistun.twitter.service.post;

import com.svistun.twitter.dto.PostDto;
import com.svistun.twitter.entity.Post;
import com.svistun.twitter.repository.PersonRepo;
import com.svistun.twitter.repository.PostRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {
    private final PostRepo postRepo;
    private final PersonRepo personRepo;

    @Override
    @Transactional
    public Post savePost(PostDto postDto, Authentication authentication) {
        Post post = new Post();
        BeanUtils.copyProperties(postDto, post);
        post.setCreationTime(LocalDateTime.now());
        post.setPerson(personRepo.findByUsername(authentication.getName()));
        return postRepo.save(post);
    }

    @Override
    public List<Post> getPost(String username) {
        return postRepo.findByPersonUsername(username);
    }
}
