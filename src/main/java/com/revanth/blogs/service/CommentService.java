package com.revanth.blogs.service;

import com.revanth.blogs.entity.Comment;
import com.revanth.blogs.payload.CommentDTO;

import java.util.List;

public interface CommentService {
    public CommentDTO createComment(long postId,CommentDTO commentDTO);
    public List<CommentDTO> getCommentsByPostId(long postId);
    public CommentDTO getCommentById(long postId, long commentId);
    public CommentDTO updateComment(long postId, long commentId, CommentDTO commentDTO);
    public void deleteComment(long postId, long commentId);
}
