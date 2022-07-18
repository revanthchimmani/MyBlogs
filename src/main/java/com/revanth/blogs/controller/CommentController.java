package com.revanth.blogs.controller;

import com.revanth.blogs.payload.CommentDTO;

import com.revanth.blogs.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/posts/")
public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/{postId}/comments")
    public List<CommentDTO> getCommentsByPostId(@PathVariable long postId){
        return commentService.getCommentsByPostId(postId);
    }
    @PostMapping("/{postId}/comments")
    public ResponseEntity<CommentDTO> createComment(@Valid
            @PathVariable long postId,
            @RequestBody CommentDTO commentRequest){
        return new ResponseEntity<>(commentService.createComment(postId,commentRequest), HttpStatus.CREATED);
    }

    @GetMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDTO> getCommentsByPostId(@PathVariable long postId,@PathVariable long commentId){
        return new ResponseEntity<>(commentService.getCommentById(postId,commentId),HttpStatus.OK);
    }

    @PutMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDTO> updateCommentsByPostId(@Valid  @PathVariable long postId,
                                                             @PathVariable long commentId,
                                                             @RequestBody CommentDTO commentDTO){
        return new ResponseEntity<>(commentService.updateComment(postId, commentId, commentDTO),HttpStatus.OK);
    }

    @DeleteMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable long postId,
                                                @PathVariable long commentId){
        commentService.deleteComment(postId,commentId);
        return new ResponseEntity<>("Comment got deleted", HttpStatus.OK);
    }
}
