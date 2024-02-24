package com.add.course.repository;

import com.add.course.model.Choice;
import com.add.course.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, String> {

    Optional<Question> findQuestionByChoices(Choice choice);

}
