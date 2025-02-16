package com.avaliakids.services;

import com.avaliakids.models.Question;
import com.avaliakids.models.QuizAnswer;
import com.avaliakids.repositories.QuestionRepository;
import com.avaliakids.repositories.QuizAnswerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuizService {
    
    private final QuizAnswerRepository quizAnswerRepository;
    private final QuestionRepository questionRepository;

    public QuizService(QuizAnswerRepository quizAnswerRepository, QuestionRepository questionRepository) {
        this.quizAnswerRepository = quizAnswerRepository;
        this.questionRepository = questionRepository;
    }

    public boolean hasStudentAlreadyAnswered(String studentId, String questionId) {
        return quizAnswerRepository.existsByStudentIdAndQuestionId(studentId, questionId);
    }

    public boolean saveAnswer(QuizAnswer answer) {
        if (hasStudentAlreadyAnswered(answer.getStudentId(), answer.getQuestionId())) {
            return false;
        }

        Optional<Question> questionOpt = questionRepository.findById(answer.getQuestionId());

        if (questionOpt.isPresent()) {
            Question question = questionOpt.get();

            boolean isCorrect = question.getCorrectOption().trim().equalsIgnoreCase(answer.getSelectedOption().trim());

            answer.setCorrect(isCorrect);

            quizAnswerRepository.save(answer);
            return true;
        } else {
            return false;
        }
    }

    public List<QuizAnswer> getAnswersByStudent(String studentId) {
        return quizAnswerRepository.findByStudentId(studentId);
    }
}
