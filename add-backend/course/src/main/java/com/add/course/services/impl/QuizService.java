package com.add.course.services.impl;

import com.add.course.constants.TypeQuestion;
import com.add.course.dto.*;
import com.add.course.exception.ApiException;
import com.add.course.model.Choice;
import com.add.course.model.Question;
import com.add.course.model.Quiz;
import com.add.course.model.Week;
import com.add.course.repository.*;
import com.add.course.services.IQuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuizService implements IQuizService {

    private final WeekRepository weekRepository;

    private final ActivityRepository activityRepository;

    private final QuestionRepository questionRepository;

    private final ChoiceRepository choiceRepository;

    private final QuizRepository quizRepository;

    @Override
    public Optional<Week> createQuiz(String weekId, QuizDTO quizDTO){
        return Optional.of(weekId)
                .map(e -> weekRepository.findById(weekId))
                .filter(e -> e.isPresent())
                .map(e -> e.get())
                .map(e -> {

                    if(isValidActivityTitle(quizDTO.getTitle())) {
                        if(isValidActivityNumber(weekId, quizDTO.getNumber())){

                            Quiz quiz = Quiz.builder()
                                    .title(quizDTO.getTitle())
                                    .number(quizDTO.getNumber())
                                    .build();

                            if(quizDTO.getQuestions() != null) {

                                quizDTO.getQuestions().stream().forEach(question -> {
                                    buildQuestions(quiz, question);
                                });

                            }

                            activityRepository.save(quiz);

                            e.getActivities().add(quiz);

                            return Optional.of(weekRepository.save(e));

                        } else
                            throw new ApiException("Number not valid");
                    } else
                        throw new ApiException("Title not valid");

                })
                .orElseThrow(() -> new ApiException("Week with id: "+ weekId +" not  found"));
    }

    @Override
    public Optional<Quiz> addQuestions(String quizId, List<QuestionDTO> questionDTOList){
        return Optional.of(quizId)
                .map(e -> quizRepository.findById(quizId))
                .filter(e -> e.isPresent())
                .map(e -> e.get())
                .map(e -> {

                    if (questionDTOList != null){

                        questionDTOList.stream().forEach(question -> {
                            buildQuestions(e, question);
                        });

                    }

                    return Optional.of(activityRepository.save(e));

                })
                .orElseThrow( () -> new ApiException("Quiz with id: "+ quizId +" not  found"));

    }

    @Override
    public Optional<Question> addChoices(String questionId, List<ChoiceDTO> choiceDTOList){
        return Optional.of(questionId)
                .map(e -> questionRepository.findById(questionId))
                .filter(e -> e.isPresent())
                .map(e -> e.get())
                .map(e -> {

                    if (choiceDTOList != null){

                        choiceDTOList.stream().forEach(choice -> {
                            buildChoices(e, choice);
                        });

                    }

                    return Optional.of(questionRepository.save(e));

                })
                .orElseThrow( () -> new ApiException("Question with id: "+ questionId +" not  found"));

    }

    @Override
    public Optional<Quiz> updateQuiz(String quizId, UpdateQuizDTO updateQuizDTO){
        return Optional.of(quizId)
                .map(e -> quizRepository.findById(quizId))
                .filter(e -> e.isPresent())
                .map(e -> e.get())
                .map(e -> {

                    String weekId = weekRepository.findWeekByActivities(e).get().getId();

                    if(isValidActivityTitle(updateQuizDTO.getTitle()))
                        e.setTitle(updateQuizDTO.getTitle());

                    if(isValidActivityNumber(weekId, updateQuizDTO.getNumber()))
                        e.setNumber(updateQuizDTO.getNumber());

                    return Optional.of(activityRepository.save(e));
                }).orElseThrow( () -> new ApiException("Quiz with id: "+ quizId +" not  found"));
    }

    @Override
    public Optional<Question> updateQuestion(String questionId, UpdateQuestionDTO updateQuestionDTO){
        return Optional.of(questionId)
                .map(e -> questionRepository.findById(questionId))
                .filter(e -> e.isPresent())
                .map(e -> e.get())
                .map(e -> {

                    if(updateQuestionDTO.getQuestionContent() != null)
                        e.setQuestion(updateQuestionDTO.getQuestionContent());

                    if(updateQuestionDTO.getType() != null)
                        e.setType(
                                updateQuestionDTO.getType() == "solus" || updateQuestionDTO.getType() == "polyresponse"
                                        ?
                                        TypeQuestion.getByValue(updateQuestionDTO.getType())
                                        :
                                        null
                        );


                    return Optional.of(questionRepository.save(e));
                }).orElseThrow( () -> new ApiException("Question with id: "+ questionId +" not  found"));
    }

    @Override
    public Optional<Choice> updateChoice(String choiceId, ChoiceDTO choiceDTO){
        return Optional.of(choiceId)
                .map(e -> choiceRepository.findById(choiceId))
                .filter(e -> e.isPresent())
                .map(e -> e.get())
                .map(e -> {

                    if(choiceDTO.getChoice() != null)
                        e.setChoice(choiceDTO.getChoice());

                    if(choiceDTO.getIsTrue() != null)
                        e.setTrue(Boolean.valueOf(choiceDTO.getIsTrue()));

                    return Optional.of(choiceRepository.save(e));
                }).orElseThrow( () -> new ApiException("Choice with id: "+ choiceId +" not  found"));
    }

    @Override
    public void deleteQuiz(String quizId){
        Optional.of(quizId)
                .map(a -> activityRepository.findById(a))
                .filter(a -> a.isPresent())
                .map(a -> a.get())
                .ifPresent(a -> {

                    String weekId = weekRepository.findWeekByActivities(a).get().getId();

                    Optional.of(weekId)
                            .map(w -> weekRepository.findById(w))
                            .filter(w -> w.isPresent())
                            .map(w -> w.get())
                            .map(w -> {
                                w.getActivities().remove(a);
                                return weekRepository.save(w);
                            }).orElseThrow(() -> new ApiException("Week not found"));

                    activityRepository.delete(a);
                });
    }

    @Override
    public void deleteQuestion(String questionId){
        Optional.of(questionId)
                .map(q -> questionRepository.findById(q))
                .filter(q -> q.isPresent())
                .map(q -> q.get())
                .ifPresent(q -> {

                    String quizId = quizRepository.findQuizByQuestions(q).get().getId();

                    Optional.of(quizId)
                            .map(w -> quizRepository.findById(w))
                            .filter(w -> w.isPresent())
                            .map(w -> w.get())
                            .map(w -> {
                                w.getQuestions().remove(q);
                                return activityRepository.save(w);
                            }).orElseThrow(() -> new ApiException("Question not found"));

                    questionRepository.delete(q);
                });
    }

    @Override
    public void deleteChoice(String choiceId){
        Optional.of(choiceId)
                .map(c -> choiceRepository.findById(c))
                .filter(c -> c.isPresent())
                .map(c -> c.get())
                .ifPresent(c -> {

                    Optional.of(c)
                            .map(w -> questionRepository.findQuestionByChoices(w))
                            .filter(question -> question.isPresent())
                            .map(question -> question.get())
                            .map(question -> {
                                question.getChoices().remove(c);
                                return questionRepository.save(question);
                            }).orElseThrow(() -> new ApiException("Question not found"));

                    choiceRepository.delete(c);

                });
    }

    public Quiz buildQuestions(Quiz quiz, QuestionDTO questionDTO){

        if(quiz != null && questionDTO != null){


                Question question = Question.builder()
                        .question(questionDTO.getQuestionContent())
                        .type(
                                questionDTO.getType().equals("solus") || questionDTO.getType().equals("polyresponse")
                                        ?
                                        TypeQuestion.getByValue(questionDTO.getType())
                                        :
                                        null
                        )
                        .build();

                if(questionDTO.getChoices() != null){

                    questionDTO.getChoices().stream().forEach(choice -> {
                        buildChoices(question, choice);
                    });

                }

                questionRepository.save(question);

                quiz.getQuestions().add(question);

        }

        return quiz;
    }

    public Question buildChoices(Question question, ChoiceDTO choiceDTO){

        if(question != null && choiceDTO != null) {

                Choice choice = Choice.builder()
                        .choice(choiceDTO.getChoice())
                        .isTrue(choiceDTO.getIsTrue() != null ? Boolean.valueOf(choiceDTO.getIsTrue()) : null)
                        .build();

                choiceRepository.save(choice);

                question.getChoices().add(choice);

        }

        return question;
    }

    public boolean isValidActivityTitle(String title){
        // Check if the title is blank or null
        if (title == null || title.trim().isEmpty())
            return false;

        // Check if the title consists of repeated spaces
        if (title.matches("^\\s+$"))
            return false;

        // All checks passed, title is valid
        return true;
    }

    public boolean isValidActivityNumber(String weekId, int number){

        if(number <= 0)
            return false;

        // Check if the number exists on db
        if(weekRepository.findById(weekId)
                .get()
                .getActivities()
                .stream()
                .map(week -> week.getNumber())
                .collect(Collectors.toList())
                .contains(number)
        )
            return false;

        // All checks passed, title is valid
        return true;

    }

}
