package com.svistun.twitter.facade.post;

import com.svistun.twitter.dto.CreatePostDto;
import com.svistun.twitter.dto.PostDto;
import com.svistun.twitter.entity.Post;
import com.svistun.twitter.facade.comment.CommentFacadeImpl;
import com.svistun.twitter.facade.person.PersonFacadeImpl;
import com.svistun.twitter.service.post.PostServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
@Slf4j
public class PostFacadeImpl implements PostFacade {
    private final PersonFacadeImpl personFacade;
    private final CommentFacadeImpl commentFacade;
    private final PostServiceImpl postService;


    @Override
    public List<PostDto> transactionFromEntityToDTO(List<Post> posts, Pageable pageable) {
        List<PostDto> postDtoList = new ArrayList<>();
        for (Post post : posts) {
            PostDto postDto = transactionFromEntityToDTO(post);
            postDtoList.add(postDto);
        }
        return postDtoList;
    }

    @Override
    public PostDto transactionFromEntityToDTO(Post post) {
        PostDto postDto = new PostDto();
        BeanUtils.copyProperties(post, postDto);
        postDto.setCreator(personFacade.transactionFromEntityToDTO(post.getPerson()));
        postDto.setComments(commentFacade.transactionFromEntityToDTO(post.getComments()));
        return postDto;
    }

    @Override
    @Transactional
    public PostDto savePost(CreatePostDto createPostDto, Authentication authentication) {
        try {
            Post post = new Post();
            BeanUtils.copyProperties(createPostDto, post);
            post.setComments(new ArrayList<>());
            post.setPerson(personFacade.findPersonByEmail(authentication.getName()));
            post.setCreationTime(LocalDateTime.now());
            postService.savePost(post);
            return transactionFromEntityToDTO(post);
        } catch (Exception e){
            //fix man
            log.info("error");
            throw new RuntimeException();
        }
    }

    @Override
    public List<PostDto> findAllPostByUsername(String username, Pageable pageable) {
        Page<Post> post = postService.findAllPostByUsername(username, pageable);
        return transactionFromEntityToDTO(post.getContent(), pageable);
    }

    @Override
    public List<PostDto> findAllPostByEmail(String email, Pageable pageable) {
        Page<Post> post = postService.findAllPostByEmail(email, pageable);
        return transactionFromEntityToDTO(post.getContent(), pageable);
    }

}
