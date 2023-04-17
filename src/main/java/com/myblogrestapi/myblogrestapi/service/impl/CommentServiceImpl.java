package com.myblogrestapi.myblogrestapi.service.impl;

import com.myblogrestapi.myblogrestapi.entities.Comment;
import com.myblogrestapi.myblogrestapi.entities.Post;
import com.myblogrestapi.myblogrestapi.exception.ResourceNotFoundException;
import com.myblogrestapi.myblogrestapi.payload.CommentDto;
import com.myblogrestapi.myblogrestapi.repository.CommentRepository;
import com.myblogrestapi.myblogrestapi.repository.PostRepo;
import com.myblogrestapi.myblogrestapi.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private CommentRepository commentRepository;
    private PostRepo postRepository;
    private ModelMapper mapper;
    public CommentServiceImpl(CommentRepository commentRepository, PostRepo postRepository, ModelMapper mapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.mapper = mapper;
    }






    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId));
        Comment comment = mapToComment(commentDto);
        comment.setPost(post);
        Comment newComment = commentRepository.save(comment);
        return mapToDTO(newComment);
    }

    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);

        return comments.stream().map(comment -> mapToDTO(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDto updateComment(Long postId, Long commentId, CommentDto commentDto) {
        // retrieve post entity by id
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId));

        // retrieve comment by id
        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new ResourceNotFoundException("Comment", "id", commentId));
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

        Comment updatedComment = commentRepository.save(comment);
        return mapToDTO(updatedComment);



    }

    @Override
    public void deleteComment(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId));


        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new ResourceNotFoundException("Comment", "id", commentId));
        commentRepository.deleteById(commentId);
    }

    CommentDto mapToDTO(Comment comment) {
        CommentDto commentDto = mapper.map(comment, CommentDto.class);
//         CommentDto commentDto = new CommentDto();
//
//         commentDto.setId(comment.getId());
//         commentDto.setName(comment.getName());
//         commentDto.setEmail(comment.getEmail());
//         commentDto.setBody(comment.getBody());
         return  commentDto;

     }

    Comment mapToComment(CommentDto commentDto) {
        Comment comment = mapper.map(commentDto, Comment.class);
//        Comment comment = new Comment();
//        comment.setId(commentDto.getId());
//        comment.setName(commentDto.getName());
//        comment.setEmail(commentDto.getEmail());
//        comment.setBody(commentDto.getBody());
        return  comment;
    }

}
