package com.revanth.blogs.controller;

import com.revanth.blogs.entity.Post;
import com.revanth.blogs.payload.PostDTO;
import com.revanth.blogs.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private PostService postService;

    public PostController(PostService postService){
        this.postService=postService;
    }

    //create blog post
    @PostMapping
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postRequestBody){
        return new ResponseEntity<>(postService.createPost(postRequestBody), HttpStatus.CREATED);
    }

    @GetMapping
    public List<PostDTO> getAllPosts(){
        return postService.getALLPosts();
    }
    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable(name="id") long id){
        return ResponseEntity.ok(postService.getPostById(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<PostDTO> updatePost(@RequestBody PostDTO postDTO, @PathVariable(name = "id") long id){
        return ResponseEntity.ok(postService.updatePost(postDTO,id));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable(name="id") long id){
        postService.deletePostById(id);
        return ResponseEntity.ok("Post entity deleted Successfully");
    }
}
