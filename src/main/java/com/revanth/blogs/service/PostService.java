package com.revanth.blogs.service;

import com.revanth.blogs.payload.PostDTO;
import com.revanth.blogs.payload.PostResponse;

public interface PostService {
    PostDTO createPost(PostDTO postDTO);

    PostResponse getALLPosts(int pageNo, int pageSize);

    PostDTO getPostById(long id);

    PostDTO updatePost(PostDTO postDTO,long id);

    void deletePostById(long id);
}
