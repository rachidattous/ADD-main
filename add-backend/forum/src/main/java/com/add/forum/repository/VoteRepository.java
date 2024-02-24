package com.add.forum.repository;

import com.add.forum.model.Post;
import com.add.forum.model.User;
import com.add.forum.model.Vote;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, String> {

    Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User user);

    boolean existsByPost_IdAndUser_Id(String postId, String userId);

    List<Vote> findByPost(Post post);

    Page<Vote> findByPost(Post post, Pageable pageable);
}
