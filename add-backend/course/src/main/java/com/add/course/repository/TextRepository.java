package com.add.course.repository;

import com.add.course.model.Text;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TextRepository extends JpaRepository<Text, String> {
}
