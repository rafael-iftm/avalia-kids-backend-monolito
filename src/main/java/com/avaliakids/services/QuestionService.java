package com.avaliakids.services;

import com.avaliakids.models.Question;
import com.avaliakids.repositories.QuestionRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public List<Question> getQuestionsByClassLevel(String classLevel) {
        return questionRepository.findByClassLevel(classLevel);
    }

    public Question addQuestion(Question question) {
        return questionRepository.save(question);
    }
}
