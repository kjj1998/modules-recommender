package com.project.modulesRecommender.student.services;

import com.project.modulesRecommender.exceptions.CustomErrorException;
import com.project.modulesRecommender.module.models.Module;
import com.project.modulesRecommender.student.models.*;
import com.project.modulesRecommender.repositories.ModuleRepository;
import com.project.modulesRecommender.repositories.StudentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final ModuleRepository moduleRepository;

    public StudentService(StudentRepository studentRepository, ModuleRepository moduleRepository) {
        this.studentRepository = studentRepository;
        this.moduleRepository = moduleRepository;
    }

    /**
     * Update the student details except for studentId which is unique and fixed upon account creation
     *
     * @param studentDTO the data transfer object containing the updated details of the student
     * @return the student object containing the updated details
     * @since 1.0
     */
    public StudentDTO updateStudent(StudentDTO studentDTO) {
        if (!studentRepository.existsById(studentDTO.getStudentId())) {
            throw new CustomErrorException(
                    HttpStatus.BAD_REQUEST,
                    "Student with id " + studentDTO.getStudentId() + " does not exist!");
        }

        List<String> courseCodes = studentDTO.getCourseCodes();
        List<Module> modulesTaken = new ArrayList<>();

        for (String courseCode : courseCodes) {
            Optional<Module> module = moduleRepository.findById(courseCode);
            module.ifPresent(modulesTaken::add);
        }

        if (modulesTaken.size() != courseCodes.size()) {
            throw new CustomErrorException(
                    HttpStatus.BAD_REQUEST,
                    "Some of the course codes entered are invalid!");
        }

        Student retrievedStudent = studentRepository.findById(studentDTO.getStudentId()).get();

        retrievedStudent.setMajor(studentDTO.getMajor());
        retrievedStudent.setFirstName(studentDTO.getFirstName());
        retrievedStudent.setLastName(studentDTO.getLastName());
        retrievedStudent.setYearOfStudy(studentDTO.getYearOfStudy());
        retrievedStudent.setEmail(studentDTO.getEmail());
        retrievedStudent.setModules(modulesTaken);

        Student updatedStudent = studentRepository.save(retrievedStudent);

        return StudentDTO.builder()
                .firstName(updatedStudent.getFirstName())
                .lastName(updatedStudent.getLastName())
                .email(updatedStudent.getEmail())
                .studentId(updatedStudent.getStudentId())
                .major(updatedStudent.getMajor())
                .courseCodes(updatedStudent.getCourseCodes())
                .yearOfStudy(updatedStudent.getYearOfStudy())
                .build();
    }

    public void deleteStudent(String studentId) {
        if (!studentRepository.existsById(studentId)) {
            throw new CustomErrorException(
                    HttpStatus.BAD_REQUEST,
                    "Student with id " + studentId + " does not exist!");
        }

        studentRepository.deleteById(studentId);

        if (studentRepository.findById(studentId).isPresent()) {
            throw new CustomErrorException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Student with id " + studentId + " was not deleted successfully!");
        }
    }

    public StudentDTO readStudent(String studentId) {
        if (!studentRepository.existsById(studentId)) {
            throw new CustomErrorException(
                    HttpStatus.BAD_REQUEST,
                    "Student with id " + studentId + " does not exist!");
        }

        Student retrievedStudent = studentRepository.findById(studentId).get();

        return StudentDTO.builder()
                .firstName(retrievedStudent.getFirstName())
                .lastName(retrievedStudent.getLastName())
                .email(retrievedStudent.getEmail())
                .studentId(retrievedStudent.getStudentId())
                .major(retrievedStudent.getMajor())
                .courseCodes(retrievedStudent.getCourseCodes())
                .yearOfStudy(retrievedStudent.getYearOfStudy())
                .build();
    }
}