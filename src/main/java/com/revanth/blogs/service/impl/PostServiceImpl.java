package com.revanth.blogs.service.impl;

import com.revanth.blogs.entity.Post;
import com.revanth.blogs.exception.ResourceNotFoundException;
import com.revanth.blogs.payload.PostDTO;
import com.revanth.blogs.repository.PostRepository;
import com.revanth.blogs.service.PostService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository){
        this.postRepository=postRepository;
    }
    @Override
    public PostDTO createPost(PostDTO postDTO) {

        //convert DTO to entity
        Post post = mapToEntity(postDTO);
        Post createdPost = postRepository.save(post);
        //convert Post -> PostDTO
        PostDTO postResponse = mapToDTO(createdPost);
        return postResponse;
    }

    @Override
    public List<PostDTO> getALLPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream().map(post->mapToDTO(post)).collect(Collectors.toList());
    }

    @Override
    public PostDTO getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post", "id", id));
        return mapToDTO(post);
    }

    @Override
    public PostDTO updatePost(PostDTO postDTO, long id) {
        Post post = postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post", "id",id));
        post.setContent(postDTO.getContent());
        post.setTitle(postDTO.getTitle());
        Post updatedPost = postRepository.save(post);
        return mapToDTO(updatedPost);
    }

    @Override
    public void deletePostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("delete", "id", id));
        postRepository.delete(post);
    }

    //convert Entity into DTO
    private PostDTO mapToDTO(Post post){
        PostDTO postDTO=new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setTitle(post.getTitle());
        postDTO.setDescription(post.getDescription());
        postDTO.setContent(post.getContent());
        return postDTO;
    }
    // convert DTO to Entity
    private Post mapToEntity(PostDTO postDTO){
        Post post = new Post();
        post.setTitle(postDTO.getTitle());
        post.setDescription(postDTO.getDescription());
        post.setContent(postDTO.getContent());
        return post;
    }
}
