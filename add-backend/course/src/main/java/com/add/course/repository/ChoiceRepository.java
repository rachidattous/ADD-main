package com.add.course.repository;

import com.add.course.model.Choice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChoiceRepository extends JpaRepository<Choice, String> {
}
