package com.add.file.repository;

import com.add.file.model.Content;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ContentRepository extends JpaRepository<Content, String>, JpaSpecificationExecutor<Content> {

  Page<Content> findByIdIn(List<String> ids, Pageable pageable);

}
