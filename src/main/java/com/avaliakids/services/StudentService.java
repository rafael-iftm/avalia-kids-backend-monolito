package com.avaliakids.services;

import com.avaliakids.models.Student;
import com.avaliakids.repositories.StudentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    private String calculateClass(String birthDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate birthDateParsed = LocalDate.parse(birthDate, formatter);
        int currentYear = LocalDate.now().getYear();
    
        int yearTurningSix = birthDateParsed.getYear() + 6;
        LocalDate cutOffDate = LocalDate.of(yearTurningSix, 3, 31);
    
        int entryYear;
        if (!birthDateParsed.isAfter(cutOffDate)) {
            entryYear = yearTurningSix;
        } else {
            entryYear = yearTurningSix + 1;
        }
    
        int classYear = 1 + (currentYear - entryYear);
    
        if (classYear < 1) {
            classYear = 1;
        } else if (classYear > 4) {
            classYear = 4;
        }
    
        return classYear + "º Ano";
    }    

    public Student registerStudent(String name, String birthDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate birthDateParsed = LocalDate.parse(birthDate, formatter);
        int age = LocalDate.now().getYear() - birthDateParsed.getYear();
    
        if (age < 5 || age > 12) {
            throw new IllegalArgumentException("A idade permitida para cadastro é entre 5 e 12 anos.");
        }
    
        String className = calculateClass(birthDate);
    
        Student student = new Student();
        student.setName(name);
        student.setBirthDate(birthDate);
        student.setClassName(className);
        student.setScore(null);
    
        return studentRepository.save(student);
    }    
}
