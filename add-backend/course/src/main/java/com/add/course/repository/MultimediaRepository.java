package com.add.course.repository;

import com.add.course.model.Multimedia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MultimediaRepository extends JpaRepository<Multimedia, String> {
}
