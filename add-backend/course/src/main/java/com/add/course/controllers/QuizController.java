package com.add.course.controllers;

import com.add.course.dto.*;
import com.add.course.model.Choice;
import com.add.course.model.Question;
import com.add.course.model.Quiz;
import com.add.course.model.Week;
import com.add.course.services.IQuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/course/activity/quiz")
public class QuizController {

    private final IQuizService iQuizService;

    @PostMapping("/{weekId}")
    public Optional<Week> createQuiz(@PathVariable String weekId, @RequestBody QuizDTO quizDTO){
        return iQuizService.createQuiz(weekId, quizDTO);
    }

    @PostMapping("/question/{quizId}")
    public Optional<Quiz> addQuestions(@PathVariable String quizId, @RequestBody List<QuestionDTO> questionDTOList){
        return iQuizService.addQuestions(quizId, questionDTOList);
    }

    @PostMapping("/question/choice/{questionId}")
    public Optional<Question> addChoices(@PathVariable String questionId, @RequestBody List<ChoiceDTO> choiceDTOList){
        return iQuizService.addChoices(questionId, choiceDTOList);
    }

    @PutMapping(value = "/{quizId}")
    public Optional<Quiz> updateQuiz(@PathVariable String quizId, UpdateQuizDTO updateQuizDTO){
        return iQuizService.updateQuiz(quizId, updateQuizDTO);
    }

    @PutMapping(value = "/question/{questionId}")
    public Optional<Question> updateQuestion(@PathVariable String questionId, UpdateQuestionDTO updateQuestionDTO){
        return iQuizService.updateQuestion(questionId, updateQuestionDTO);
    }

    @PutMapping(value = "/question/choice/{choiceId}")
    public Optional<Choice> updateChoice(@PathVariable String choiceId, ChoiceDTO choiceDTO){
        return iQuizService.updateChoice(choiceId, choiceDTO);
    }

    @DeleteMapping(value = "/{quizId}")
    public void deleteQuiz(@PathVariable String quizId){
        iQuizService.deleteQuiz(quizId);
    }

    @DeleteMapping(value = "/question/{questionId}")
    public void deleteQuestion(@PathVariable String questionId){
        iQuizService.deleteQuestion(questionId);
    }

    @DeleteMapping(value = "/question/choice/{choiceId}")
    public void deleteChoice(@PathVariable String choiceId){
        iQuizService.deleteChoice(choiceId);
    }

}
