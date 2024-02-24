package com.add.forum.services;

import com.add.forum.dto.VoteDTO;
import com.add.forum.exception.vote.VoteAlreadySubmittedByUserException;
import com.add.forum.exception.vote.VoteNotFoundException;

import com.add.forum.model.Post;
import com.add.forum.model.Vote;

import com.add.forum.repository.PostRepository;
import com.add.forum.repository.VoteRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;

    private final PostRepository postRepository;

    private final UserService userService;

    private final ModelMapper modelMapper;

    public Optional<Vote> createVote(VoteDTO voteDTO) {
        if (voteRepository.existsByPost_IdAndUser_Id(voteDTO.getPostId(), voteDTO.getUserId())) {
            throw new VoteAlreadySubmittedByUserException();
        }
        Vote vote = Vote.builder()
                .voteType(voteDTO.getVoteType())
                .post(postRepository.findById(voteDTO.getPostId()).orElse(null))
                .user(userService.getOrCreate(voteDTO.getUserId()))
                .build();

        return Optional.of(voteRepository.save(vote));

    }

    public Vote getVoteById(@Valid @NotNull @NotBlank String voteId) {

        return voteRepository.findById(voteId).orElseThrow(VoteNotFoundException::new);

    }

    public Page<Vote> getVotesByPost(@Valid @NotNull Post post, Pageable pageable) {

        return voteRepository.findByPost(post, pageable);

    }

    public Optional<Vote> updateVote(@Valid @NotNull @NotBlank String voteId,
            @Valid @NotNull VoteDTO voteDTO) {

        Vote vote = getVoteById(voteId);
        modelMapper.map(voteDTO, vote);
        return Optional.of(voteRepository.save(vote));

    }

    public void deleteVote(@Valid @NotNull @NotBlank String voteId) {

        voteRepository.findById(voteId)
                .ifPresent(this::deleteVote);
    }

    public void deleteVote(@Valid @NotNull Vote vote) {
        log.info("deleting vote with id : {} ", vote.getId());
        voteRepository.delete(vote);

    }

    public void deleteVotes(@Valid @NotNull List<Vote> votes) {

        if (!votes.isEmpty()) {
            voteRepository.deleteAll(votes);
        }

    }

    public void deleteVotesByPost(@Valid @NotNull Post post) {

        deleteVotes(voteRepository.findByPost(post));

    }

}
