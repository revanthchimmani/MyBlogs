package com.revanth.blogs.service;

import com.revanth.blogs.payload.PostDTO;

import java.util.List;

public interface PostService {
    PostDTO createPost(PostDTO postDTO);

    List<PostDTO> getALLPosts();

    PostDTO getPostById(long id);

    PostDTO updatePost(PostDTO postDTO,long id);

    void deletePostById(long id);
}
