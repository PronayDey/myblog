package com.myblogrestapi.myblogrestapi.service;

import com.myblogrestapi.myblogrestapi.entities.Comment;
import com.myblogrestapi.myblogrestapi.payload.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(long postId, CommentDto commentDto);
    List<CommentDto> getCommentsByPostId(long postId);

    CommentDto updateComment(Long postId, Long commentId, CommentDto commentDto);

    void deleteComment(Long postId, Long commentId);
}

