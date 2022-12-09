package com.svistun.twitter.repository;

import com.svistun.twitter.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepo extends JpaRepository<Post, Long> {
    Page<Post> findByPersonUsername(String username, Pageable pageable);
    Page<Post> findByPersonEmail (String email, Pageable pageable);
    void deletePostByIdAndPersonId(Long postID, Long personID);
    Boolean existsByIdAndPersonId(Long postID, Long personID);
}
