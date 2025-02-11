package com.avaliakids.controllers;

import com.avaliakids.models.Student;
import com.avaliakids.services.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/register")
    public ResponseEntity<Student> registerStudent(@RequestParam String name, @RequestParam String birthDate) {
        try {
            Student student = studentService.registerStudent(name, birthDate);
            return ResponseEntity.ok(student);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
