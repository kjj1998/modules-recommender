package com.example.modulesrecommender.controllers;

import com.example.modulesrecommender.models.Module;
import com.example.modulesrecommender.models.Student;
import com.example.modulesrecommender.repositories.ModuleRepository;
import com.example.modulesrecommender.repositories.StudentRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentRepository studentRepository;

    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping("/{studentId}")
    Optional<Student> byStudentId(@PathVariable String studentId) {
        return studentRepository.findById(studentId);
    }
}
