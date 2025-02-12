package com.avaliakids.controllers;

import com.avaliakids.models.Question;
import com.avaliakids.services.QuestionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/{classLevel}")
    public ResponseEntity<List<Question>> getQuestionsByClassLevel(@PathVariable String classLevel) {
        List<Question> questions = questionService.getQuestionsByClassLevel(classLevel);
        return ResponseEntity.ok(questions);
    }

    @PostMapping("/add")
    public ResponseEntity<Question> addQuestion(@RequestBody Question question) {
        Question savedQuestion = questionService.addQuestion(question);
        return ResponseEntity.ok(savedQuestion);
    }
}
