package com.project.modulesRecommender.student.controllers;

import com.project.modulesRecommender.errors.HttpResponse;
import com.project.modulesRecommender.student.models.CreateStudentDTO;
import com.project.modulesRecommender.student.models.ReadStudentDTO;
import com.project.modulesRecommender.student.models.ReturnStudentDTO;
import com.project.modulesRecommender.student.services.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    /**
     * GET request to retrieve a student's details by id
     * @param studentId the id of the student to retrieve details
     * @return the response object containing the status code and retrieved student object
     * @since 1.0
     */
    @GetMapping("/{studentId}")
    ResponseEntity<HttpResponse> byStudentId(@PathVariable String studentId) {
        try {
            ReturnStudentDTO student = studentService.readStudent(studentId);

            return new ResponseEntity<>(
                    new HttpResponse(
                            HttpStatus.OK,
                            "Student with id " + studentId + " retrieved.",
                            student
                    ),
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new HttpResponse(
                            HttpStatus.INTERNAL_SERVER_ERROR,
                            "Internal Server Error",
                            null
                    ),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    /**
     * POST request to create a new student in the database
     * @param createStudentDTO DTO containing details of student to be created
     * @return the response object containing the status code and created student object
     * @since 1.0
     */
    @PostMapping("/create")
    ResponseEntity<HttpResponse> createStudent(@RequestBody CreateStudentDTO createStudentDTO) {
        try {
            ReadStudentDTO createdStudent = studentService.createStudent(createStudentDTO);

            return new ResponseEntity<>(
                    new HttpResponse(
                            HttpStatus.CREATED,
                            "Student with id " + createdStudent.getStudentId() + " created.",
                            createdStudent
                    ),
                    HttpStatus.CREATED
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new HttpResponse(
                            HttpStatus.INTERNAL_SERVER_ERROR,
                            "Internal Server Error",
                            null
                    ),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    /**
     * PUT request to update the details of a student
     * @param modifyStudentDTO the data transfer object containing the updated details of the student
     * @return the response object containing the status code and updated student object
     * @since 1.0
     */
    @PutMapping("/update")
    ResponseEntity<HttpResponse> updateStudent(@RequestBody CreateStudentDTO modifyStudentDTO) {
        try {
            ReturnStudentDTO updatedStudent = studentService.updateStudent(modifyStudentDTO);

            return new ResponseEntity<>(
                    new HttpResponse(
                            HttpStatus.OK,
                            "Student with id " + modifyStudentDTO.getStudentId() + " updated.",
                            updatedStudent
                    ),
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new HttpResponse(
                            HttpStatus.INTERNAL_SERVER_ERROR,
                            "Internal Server Error",
                            null
                    ),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    /**
     * DELETE request to delete a student
     * @param studentId the id of the student to be deleted
     * @return the response object containing the status code
     * @since 1.0
     */
    @DeleteMapping("/{studentId}")
    ResponseEntity<HttpResponse> deleteStudent(@PathVariable String studentId) {
        try {
            studentService.deleteStudent(studentId);

            return new ResponseEntity<>(
                    new HttpResponse(
                            HttpStatus.OK,
                            "Student with id " + studentId + " is deleted.",
                            null
                    ),
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new HttpResponse(
                            HttpStatus.INTERNAL_SERVER_ERROR,
                            "Internal Server Error",
                            null
                    ),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
}
