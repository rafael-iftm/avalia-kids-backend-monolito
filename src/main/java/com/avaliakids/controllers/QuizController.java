package com.avaliakids.controllers;

import com.avaliakids.models.QuizAnswer;
import com.avaliakids.services.QuizService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/quiz")
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @PostMapping("/submit")
    public ResponseEntity<?> submitAnswer(@RequestBody QuizAnswer answer) {
        boolean alreadyAnswered = quizService.hasStudentAlreadyAnswered(answer.getStudentId(), answer.getQuestionId());
    
        if (alreadyAnswered) {
            return ResponseEntity.ok().body(Map.of("message", "Resposta já foi registrada anteriormente."));
        }
    
        quizService.saveAnswer(answer);
        return ResponseEntity.ok(Map.of("message", "Resposta registrada com sucesso."));
    }    

    @GetMapping("/results/{studentId}")
    public ResponseEntity<List<QuizAnswer>> getStudentResults(@PathVariable String studentId) {
        List<QuizAnswer> results = quizService.getAnswersByStudent(studentId);
        return ResponseEntity.ok(results);
    }
}
