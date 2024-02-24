package com.add.forum.repository;

import com.add.forum.model.Comment;
import com.add.forum.model.Post;
import com.add.forum.model.User;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, String> {

    Page<Comment> findByPost(Post post, Pageable pageable);

    List<Comment> findByPost(Post post);

    Page<Comment> findAllByUser(User user, Pageable pageable);
}
