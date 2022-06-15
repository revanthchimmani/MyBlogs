package com.revanth.blogs.repository;

import com.revanth.blogs.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
