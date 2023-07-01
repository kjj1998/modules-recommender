package com.example.modulesrecommender.controllers;

import com.example.modulesrecommender.errors.HttpResponse;
import com.example.modulesrecommender.models.student.CreateStudentDTO;
import com.example.modulesrecommender.models.student.ReadStudentDTO;
import com.example.modulesrecommender.repositories.ModuleRepository;
import com.example.modulesrecommender.repositories.StudentRepository;
import com.example.modulesrecommender.services.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentRepository studentRepository;
    private final ModuleRepository moduleRepository;
    private final StudentService studentService;

    public StudentController(StudentRepository studentRepository, ModuleRepository moduleRepository, StudentService studentService) {
        this.studentRepository = studentRepository;
        this.moduleRepository = moduleRepository;
        this.studentService = studentService;
    }

    @GetMapping("/{studentId}")
    ReadStudentDTO byStudentId(@PathVariable String studentId) {
        Optional<ReadStudentDTO> student = studentRepository.findById(studentId);

        return student.orElse(null);
    }

    @PostMapping("/create")
    ResponseEntity<HttpResponse> createStudent(@RequestBody CreateStudentDTO createStudentDTO) {

        ReadStudentDTO createdStudent = studentService.createStudent(createStudentDTO);

        return new ResponseEntity<>(
                new HttpResponse(
                        HttpStatus.CREATED,
                        "Student with id " + createdStudent.getStudentId() + " created.",
                        createdStudent
                ),
                HttpStatus.CREATED
        );
    }
}
