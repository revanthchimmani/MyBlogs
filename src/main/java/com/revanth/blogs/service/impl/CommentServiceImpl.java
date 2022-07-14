package com.revanth.blogs.service.impl;

import com.revanth.blogs.entity.Comment;
import com.revanth.blogs.entity.Post;
import com.revanth.blogs.exception.BlogAPIException;
import com.revanth.blogs.exception.ResourceNotFoundException;
import com.revanth.blogs.payload.CommentDTO;
import com.revanth.blogs.repository.CommentRepository;
import com.revanth.blogs.repository.PostRepository;
import com.revanth.blogs.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private ModelMapper modelMapper;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.modelMapper= modelMapper;
    }

    @Override
    public CommentDTO createComment(long postId, CommentDTO commentDTO) {
        Comment comment = mapToComment(commentDTO);
        //retrieve post entity by postId
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("POST", "id", postId));
        // set post to commentDTO
        comment.setPost(post);
        //save comment entity to DB
        Comment savedComment = commentRepository.save(comment);
        return mapToCommentDTO(savedComment);
    }

    @Override
    public List<CommentDTO> getCommentsByPostId(long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments.stream().map(comment -> mapToCommentDTO(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDTO getCommentById(long postId, long commentId) {
        Comment comment= this.getCommentFromDB(postId,commentId);
        return mapToCommentDTO(comment);
    }

    @Override
    public CommentDTO updateComment(long postId, long commentId, CommentDTO commentDTO) {
        Comment comment= this.getCommentFromDB(postId,commentId);
        //update comment entity
        comment.setName(commentDTO.getName());
        comment.setEmail(commentDTO.getEmail());
        comment.setBody(commentDTO.getBody());

        Comment updatedComment = commentRepository.save(comment);
        return mapToCommentDTO(updatedComment);
    }

    @Override
    public void deleteComment(long postId, long commentId) {
        Comment comment= this.getCommentFromDB(postId,commentId);
        commentRepository.delete(comment);
    }

    private Comment getCommentFromDB(long postId, long commentId) {
        //retrieve post
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        //retrieve comment
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId))
        ;
        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment doest not belong to post");
        }
        return comment;
    }

    private CommentDTO mapToCommentDTO(Comment comment) {
        return modelMapper.map(comment,CommentDTO.class);
    }

    private Comment mapToComment(CommentDTO commentDTO) {
        return modelMapper.map(commentDTO,Comment.class);
    }
}
