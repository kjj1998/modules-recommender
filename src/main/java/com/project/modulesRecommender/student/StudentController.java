package com.project.modulesRecommender.student;

import com.project.modulesRecommender.errors.HttpResponse;
import com.project.modulesRecommender.student.models.StudentDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/students")
//@SecurityRequirement(name = "bearerAuth")
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
        StudentDTO student = studentService.readStudent(studentId);

        return new ResponseEntity<>(
                new HttpResponse(
                        HttpStatus.OK,
                        "Student with id " + studentId + " retrieved.",
                        student
                ),
                HttpStatus.OK
        );
    }

    /**
     * PUT request to update the details of a student
     * @param studentDTO the data transfer object containing the updated details of the student
     * @return the response object containing the status code and updated student object
     * @since 1.0
     */
    @PutMapping("/update")
    ResponseEntity<HttpResponse> updateStudent(@RequestBody StudentDTO studentDTO) {
        StudentDTO updatedStudent = studentService.updateStudent(studentDTO);

        return new ResponseEntity<>(
                new HttpResponse(
                        HttpStatus.OK,
                        "Student with id " + studentDTO.getStudentId() + " updated.",
                        updatedStudent
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
