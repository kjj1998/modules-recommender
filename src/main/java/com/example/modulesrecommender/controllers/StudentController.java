package com.example.modulesrecommender.controllers;

import com.example.modulesrecommender.errors.HttpResponse;
import com.example.modulesrecommender.models.student.CreateStudentDTO;
import com.example.modulesrecommender.models.student.ReadStudentDTO;
import com.example.modulesrecommender.models.student.ReturnStudentDTO;
import com.example.modulesrecommender.repositories.ModuleRepository;
import com.example.modulesrecommender.repositories.StudentRepository;
import com.example.modulesrecommender.services.StudentService;
import org.neo4j.cypherdsl.core.Create;
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

    /**
     * PUT request to update the details of a student
     * @param modifyStudentDTO the data transfer object containing the updated details of the student
     * @return the response object containing the status code and updated student object
     * @since 1.0
     */
    @PutMapping("/update")
    ResponseEntity<HttpResponse> updateStudent(@RequestBody CreateStudentDTO modifyStudentDTO) {
        ReadStudentDTO updatedStudent = studentService.updateStudent(modifyStudentDTO);
        ReturnStudentDTO returnStudent = new ReturnStudentDTO(
                updatedStudent.getStudentId(),
                updatedStudent.getMajor(),
                updatedStudent.getName(),
                updatedStudent.getYearOfStudy(),
                updatedStudent.getCourseCodes());

        return new ResponseEntity<>(
                new HttpResponse(
                        HttpStatus.OK,
                        "Student with id " + updatedStudent.getStudentId() + " updated.",
                        returnStudent
                ),
                HttpStatus.OK
        );
    }

    /**
     * DELETE request to delete a student
     * @param studentId the id of the student to be deleted
     * @return the response object containing the status code
     * @since 1.0
     */
    @DeleteMapping("/{studentId}")
    ResponseEntity<HttpResponse> deleteStudent(@PathVariable String studentId) {
        studentService.deleteStudent(studentId);

        return new ResponseEntity<>(
                new HttpResponse(
                        HttpStatus.OK,
                        "Student with id " + studentId + " is deleted.",
                        null
                ),
                HttpStatus.OK
        );
    }
}
