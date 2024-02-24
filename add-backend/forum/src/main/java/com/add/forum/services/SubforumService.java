package com.add.forum.services;

import com.add.forum.dto.SubforumDTO;
import com.add.forum.exception.post.PostNotFoundException;

import com.add.forum.model.Subforum;

import com.add.forum.repository.SubforumRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import org.springframework.validation.annotation.Validated;

import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Slf4j
@Service
@Validated
@RequiredArgsConstructor
public class SubforumService {

    private final UserService userService;

    private final SubforumRepository subforumRepository;

    private final PostService postService;

    private final ModelMapper modelMapper;

    public Subforum getSubforumById(@Valid @NotNull @NotBlank String subforumId) {

        return subforumRepository.findById(subforumId).orElseThrow(PostNotFoundException::new);

    }

    public Optional<Subforum> create(@Valid SubforumDTO subforumDTO) {
        Subforum subforum = Subforum.builder()
                .name(subforumDTO.getName())
                .description(subforumDTO.getDescription())
                .user(userService.getOrCreate(subforumDTO.getUserId()))
                .build();

        return Optional.of(subforumRepository.save(subforum));
    }

    public Optional<Subforum> updateSybforum(@Valid @NotNull @NotBlank String subforumId,
            @Valid @NotNull SubforumDTO subforumDTO) {

        Subforum subforum = getSubforumById(subforumId);
        modelMapper.map(subforumDTO, subforum);
        return Optional.of(subforumRepository.save(subforum));

    }

    public void deleteSubforum(@Valid @NotNull @NotBlank String subforumId) {

        subforumRepository.findById(subforumId)
                .ifPresent(this::deleteSubforum);
    }

    public void deleteSubforum(@Valid @NotNull Subforum subforum) {
        log.info("deleting subforum with id : {} ", subforum.getId());
        postService.deletePostBySubforum(subforum);
        subforumRepository.delete(subforum);

    }
}
