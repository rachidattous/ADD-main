package com.add.forum.services;

import com.add.forum.dto.PostDTO;

import com.add.forum.exception.post.PostNotFoundException;

import com.add.forum.model.Comment;
import com.add.forum.model.Post;
import com.add.forum.model.Subforum;
import com.add.forum.model.Vote;
import com.add.forum.repository.PostRepository;
import com.add.forum.repository.SubforumRepository;

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
public class PostService {

    private final PostRepository postRepository;

    private final SubforumRepository subforumRepository;

    private final UserService userService;

    private final CommentsService commentsService;

    private final VoteService voteService;

    private final ModelMapper modelMapper;

    public Post getPostById(@Valid @NotNull @NotBlank String postId) {

        return postRepository.findById(postId).orElseThrow(PostNotFoundException::new);

    }

    public Page<Post> getPostsBySubforum(@Valid @NotNull Subforum subforum, Pageable pageable) {

        return postRepository.findAllBySubforum(subforum, pageable);

    }

    public Page<Comment> getPostComentsById(@Valid @NotNull @NotBlank String postId, Pageable pageable) {

        return postRepository.findById(postId)
                .map(e -> commentsService.getCommentByPost(e, pageable))
                .orElseThrow(PostNotFoundException::new);

    }

    public Page<Vote> getPostVotesById(@Valid @NotNull @NotBlank String postId, Pageable pageable) {

        return postRepository.findById(postId)
                .map(e -> voteService.getVotesByPost(e, pageable))
                .orElseThrow(PostNotFoundException::new);

    }

    public Optional<Post> createPost(@Valid @NotNull PostDTO postDTO) {

        Post post = Post.builder()
                .content(postDTO.getContent())
                .description(postDTO.getDescription())
                .name(postDTO.getName())
                .subforum(subforumRepository.findById(postDTO.getSubForumId()).orElse(null))
                .user(userService.createUser(postDTO.getUserId()))
                .build();
        return Optional.of(postRepository.save(post));
    }

    public Optional<Post> updatePost(@Valid @NotNull @NotBlank String postId,
            @Valid @NotNull PostDTO postDTO) {

        Post post = getPostById(postId);
        modelMapper.map(postDTO, post);
        return Optional.of(postRepository.save(post));

    }

    public void deletePost(@Valid @NotNull @NotBlank String postId) {

        postRepository.findById(postId)
                .ifPresent(this::deletePost);
    }

    public void deletePost(@Valid @NotNull Post post) {
        log.info("deleting post with id : {} ", post.getId());
        commentsService.deleteCommentsByPost(post);
        voteService.deleteVotesByPost(post);
        postRepository.delete(post);

    }

    public void deletePosts(@Valid @NotNull List<Post> posts) {
        posts.stream().forEach(this::deletePost);

    }

    public void deletePostBySubforum(@Valid @NotNull Subforum Subforum) {

        deletePosts(postRepository.findAllBySubforum(Subforum));

    }

}
