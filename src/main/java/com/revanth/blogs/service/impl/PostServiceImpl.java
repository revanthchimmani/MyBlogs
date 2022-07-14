package com.revanth.blogs.service.impl;

import com.revanth.blogs.entity.Post;
import com.revanth.blogs.exception.ResourceNotFoundException;
import com.revanth.blogs.payload.PostDTO;
import com.revanth.blogs.payload.PostResponse;
import com.revanth.blogs.repository.PostRepository;
import com.revanth.blogs.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private PostRepository postRepository;

    private ModelMapper modelMapper;
        public PostServiceImpl(PostRepository postRepository,ModelMapper modelMapper){
        this.postRepository=postRepository;
        this.modelMapper=modelMapper;
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
    public PostResponse getALLPosts(int pageNo, int pageSize) {
        PostResponse postResponse= new PostResponse();

        Pageable pageable  = PageRequest.of(pageNo, pageSize);

        //get content from Page objects
        Page<Post> postsPage = postRepository.findAll(pageable);
        List<Post> posts = postsPage.getContent();
        List<PostDTO> postDTOS= posts.stream().map(post->mapToDTO(post)).collect(Collectors.toList());
        //set postRespone obj
        postResponse.setContent(postDTOS);
        postResponse.setLast(postsPage.isLast());
        postResponse.setPageNo(postsPage.getNumber());
        postResponse.setPageSize(postsPage.getSize());
        postResponse.setTotalElements(postsPage.getTotalElements());
        postResponse.setTotalPages(postsPage.getTotalPages());
        return postResponse;

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
        return modelMapper.map(post, PostDTO.class);
    }
    // convert DTO to Entity
    private Post mapToEntity(PostDTO postDTO){
        return modelMapper.map(postDTO,Post.class);
    }
}
