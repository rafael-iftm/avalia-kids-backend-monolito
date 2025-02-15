package com.avaliakids.services;

import com.avaliakids.models.QuizAnswer;
import com.avaliakids.repositories.QuizAnswerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizService {
    
    private final QuizAnswerRepository quizAnswerRepository;

    public QuizService(QuizAnswerRepository quizAnswerRepository) {
        this.quizAnswerRepository = quizAnswerRepository;
    }

    public boolean hasStudentAlreadyAnswered(String studentId, String questionId) {
        return quizAnswerRepository.existsByStudentIdAndQuestionId(studentId, questionId);
    }

    public boolean saveAnswer(QuizAnswer answer) {
        if (hasStudentAlreadyAnswered(answer.getStudentId(), answer.getQuestionId())) {
            return false;
        }
        quizAnswerRepository.save(answer);
        return true;
    }

    public List<QuizAnswer> getAnswersByStudent(String studentId) {
        return quizAnswerRepository.findByStudentId(studentId);
    }
}
