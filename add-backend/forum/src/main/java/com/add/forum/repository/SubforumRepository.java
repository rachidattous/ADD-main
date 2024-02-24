package com.add.forum.repository;

import com.add.forum.model.Subforum;
import com.add.forum.model.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubforumRepository extends JpaRepository<Subforum, String> {

    Optional<Subforum> findByName(String subforumName);

    Page<Subforum> findAllByUser(User user, Pageable pageable);
}
