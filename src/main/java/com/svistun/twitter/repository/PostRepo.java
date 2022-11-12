package com.svistun.twitter.repository;

import com.svistun.twitter.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepo extends JpaRepository<Post, Long> {
    List<Post> findByPersonUsername(String username);
}
