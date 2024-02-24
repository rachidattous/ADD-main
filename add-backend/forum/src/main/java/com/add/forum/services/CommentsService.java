package com.add.forum.services;

import com.add.forum.dto.CommentDTO;
import com.add.forum.exception.comment.CommentNotFoundException;
import com.add.forum.model.Comment;
import com.add.forum.model.Post;
import com.add.forum.repository.CommentRepository;
import com.add.forum.repository.PostRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Slf4j
@Service
@Validated
@RequiredArgsConstructor
public class CommentsService {

    private final PostRepository postRepository;

    private final UserService userService;

    private final CommentRepository commentRepository;

    private final ModelMapper modelMapper;

    public Comment getCommentById(@Valid @NotNull @NotBlank String commentId) {

        return commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);

    }

    public Page<Comment> getCommentByPost(@Valid @NotNull Post post, Pageable pageable) {

        return commentRepository.findByPost(post, pageable);

    }

    public Optional<Comment> createComment(@Valid @NotNull CommentDTO commentDTO) {

        Comment comment = Comment.builder()
                .text(commentDTO.getText())
                .post(postRepository.findById(commentDTO.getPostId()).orElse(null))
                .user(userService.getOrCreate(commentDTO.getUserId()))
                .build();

        return Optional.of(commentRepository.save(comment));

    }

    public Optional<Comment> updateComment(@Valid @NotNull @NotBlank String commentId,
            @Valid @NotNull CommentDTO commentDTO) {

        Comment comment = getCommentById(commentId);
        modelMapper.map(commentDTO, comment);
        return Optional.of(commentRepository.save(comment));

    }

    public void deleteComment(@Valid @NotNull @NotBlank String commentId) {

        commentRepository.findById(commentId)
                .ifPresent(this::deleteComment);
    }

    public void deleteComment(@Valid @NotNull Comment comment) {
        log.info("deleting comment with id : {} ", comment.getId());
        commentRepository.delete(comment);

    }

    public void deleteComments(@Valid @NotNull List<Comment> comments) {

        if (!comments.isEmpty()) {
            commentRepository.deleteAll(comments);
        }

    }

    public void deleteCommentsByPost(@Valid @NotNull Post post) {

        deleteComments(commentRepository.findByPost(post));

    }

}
