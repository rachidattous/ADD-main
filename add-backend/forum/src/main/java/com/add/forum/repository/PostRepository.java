package com.add.forum.repository;

import com.add.forum.model.Subforum;
import com.add.forum.model.Post;
import com.add.forum.model.User;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, String> {

    Page<Post> findAllBySubforum(Subforum subforum, Pageable pageable);

    List<Post> findAllBySubforum(Subforum subforum);

    Page<Post> findByUser(User user, Pageable pageable);
}
