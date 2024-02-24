package com.add.course.repository;

import com.add.course.model.Activity;
import com.add.course.model.Question;
import com.add.course.model.Quiz;
import com.add.course.model.Week;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuizRepository extends JpaRepository<Quiz, String> {

    Optional<Quiz> findQuizByQuestions(Question question);
}
