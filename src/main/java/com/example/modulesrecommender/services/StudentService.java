package com.example.modulesrecommender.services;

import com.example.modulesrecommender.exceptions.CustomErrorException;
import com.example.modulesrecommender.models.module.Module;
import com.example.modulesrecommender.models.student.CreateStudentDTO;
import com.example.modulesrecommender.models.student.ReadStudentDTO;
import com.example.modulesrecommender.models.student.ReturnStudentDTO;
import com.example.modulesrecommender.repositories.ModuleRepository;
import com.example.modulesrecommender.repositories.StudentRepository;
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

    public ReadStudentDTO createStudent(CreateStudentDTO createStudentDTO) {
        if (studentRepository.existsById(createStudentDTO.getStudentId())) {
            throw new CustomErrorException(
                    HttpStatus.BAD_REQUEST,
                    "Student with id " + createStudentDTO.getStudentId() + " already exists!");
        }

        List<String> courseCodes = createStudentDTO.getCourseCode();
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

        ReadStudentDTO student = new ReadStudentDTO(
                createStudentDTO.getStudentId(),
                createStudentDTO.getMajor(),
                createStudentDTO.getName(),
                createStudentDTO.getYearOfStudy(),
                modulesTaken);

        return studentRepository.save(student);
    }

    /**
     * Update the student details except for studentId which is unique and fixed upon account creation
     *
     * @param modifyStudentDTO the data transfer object containing the updated details of the student
     * @return the student object containing the updated details
     * @since 1.0
     */
    public ReturnStudentDTO updateStudent(CreateStudentDTO modifyStudentDTO) {
        if (!studentRepository.existsById(modifyStudentDTO.getStudentId())) {
            throw new CustomErrorException(
                    HttpStatus.BAD_REQUEST,
                    "Student with id " + modifyStudentDTO.getStudentId() + " does not exist!");
        }

        List<String> courseCodes = modifyStudentDTO.getCourseCode();
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

        ReadStudentDTO retrievedStudent = studentRepository.findById(modifyStudentDTO.getStudentId()).get();

        retrievedStudent.setMajor(modifyStudentDTO.getMajor());
        retrievedStudent.setName(modifyStudentDTO.getName());
        retrievedStudent.setYearOfStudy(modifyStudentDTO.getYearOfStudy());
        retrievedStudent.setModules(modulesTaken);

        ReadStudentDTO updatedStudent = studentRepository.save(retrievedStudent);

        return new ReturnStudentDTO(
                updatedStudent.getStudentId(),
                updatedStudent.getMajor(),
                updatedStudent.getName(),
                updatedStudent.getYearOfStudy(),
                updatedStudent.getCourseCodes());
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
}