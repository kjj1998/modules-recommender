package com.project.modulesRecommender.student;

import com.project.modulesRecommender.exceptions.CustomErrorException;
import com.project.modulesRecommender.module.models.PrerequisiteGroup;
import com.project.modulesRecommender.module.models.Module;
import com.project.modulesRecommender.student.models.Student;
import com.project.modulesRecommender.repositories.ModuleRepository;
import com.project.modulesRecommender.repositories.StudentRepository;
import com.project.modulesRecommender.student.models.StudentDTO;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


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

        List<String> studentDtoCourseCodes = studentDTO.getCourseCodes();
        List<Module> studentDtoModules = getStudentDtoModules(studentDtoCourseCodes);

        if (studentDtoModules.size() != studentDtoCourseCodes.size()) {
            throw new CustomErrorException(
                    HttpStatus.BAD_REQUEST,
                    "Some of the course codes entered are invalid!",
                    studentDTO);
        }

        var courseCodesOfEligibleModules = this.checkPrerequisitesFulfillment(studentDtoModules);

        if (courseCodesOfEligibleModules.size() != studentDtoCourseCodes.size()) {
            Set<String> setOfValidCourses = new HashSet<>(courseCodesOfEligibleModules);
            StringBuilder sb = new StringBuilder();

            for (String studentDtoCourseCode : studentDtoCourseCodes) {
                if (!setOfValidCourses.contains(studentDtoCourseCode)) {
                    sb.append(studentDtoCourseCode).append(" ");
                }
            }

            throw new CustomErrorException(
                    HttpStatus.BAD_REQUEST,
                    "You have not taken the necessary prerequisite courses for the following course(s): " + sb.toString() + "!" ,
                    null);
        }

        Student retrievedStudent = studentRepository.findById(studentDTO.getStudentId()).get();

        retrievedStudent.setMajor(studentDTO.getMajor());
        retrievedStudent.setFirstName(studentDTO.getFirstName());
        retrievedStudent.setLastName(studentDTO.getLastName());
        retrievedStudent.setYearOfStudy(studentDTO.getYearOfStudy());
        retrievedStudent.setEmail(studentDTO.getEmail());
        retrievedStudent.setModules(studentDtoModules);
        retrievedStudent.setDisciplines(studentDTO.getDisciplines());

        Student updatedStudent = studentRepository.save(retrievedStudent);

        return StudentDTO.builder()
                .firstName(updatedStudent.getFirstName())
                .lastName(updatedStudent.getLastName())
                .email(updatedStudent.getEmail())
                .studentId(updatedStudent.getStudentId())
                .major(updatedStudent.getMajor())
                .courseCodes(updatedStudent.getCourseCodes())
                .yearOfStudy(updatedStudent.getYearOfStudy())
                .disciplines(updatedStudent.getDisciplines())
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
                .disciplines(retrievedStudent.getDisciplines())
                .build();
    }

    private List<Module> getStudentDtoModules(List<String> courseCodes) {
        List<Module> modulesTaken = new ArrayList<>();

        for (String courseCode : courseCodes) {
            Optional<Module> module = moduleRepository.findById(courseCode);
            module.ifPresent(modulesTaken::add);
        }

        return modulesTaken;
    }

    /**
     * Function to check that modules fulfill their prerequisites, the algorithm used is topological sort
     * @param studentDtoModules list of modules whose eligibility are to be checked
     * @return list of course codes of eligible modules
     * @since 1.0
     */
    public List<String> checkPrerequisitesFulfillment(List<Module> studentDtoModules) {
        Map<String, List<Set<String>>> inDegree = new HashMap<>();
        Map<String, List<String>> outDegree = new HashMap<>();

        for (Module module : studentDtoModules) {
            String currentCourseCode = module.getCourseCode();
            List<PrerequisiteGroup> prereqGroups = module.getPrerequisites();

            inDegree.put(currentCourseCode, new ArrayList<>());

            if (prereqGroups != null && !prereqGroups.isEmpty()) {
                for (PrerequisiteGroup prerequisiteGroup : prereqGroups) {
                    List<Module> prereqModules = prerequisiteGroup.getModules();

                    for (Module prereqModule : prereqModules) {
                        if (!outDegree.containsKey(prereqModule.getCourseCode())) {
                            outDegree.put(prereqModule.getCourseCode(), new ArrayList<>());
                        }
                        outDegree.get(prereqModule.getCourseCode()).add(currentCourseCode);
                    }

                    inDegree.get(currentCourseCode)
                            .add(new HashSet<>(
                                    prereqModules
                                            .stream()
                                            .map(Module::getCourseCode)
                                            .collect(Collectors.toSet()))
                            );
                }
            }
        }

        List<String> courseCodesOfEligibleModules = new ArrayList<>();
        Queue<String> queue = new LinkedList<>();

        for (String courseCode : inDegree.keySet()) {
            if (inDegree.get(courseCode).isEmpty()) {
                queue.add(courseCode);
            }
        }

        while (!queue.isEmpty()) {
            String curCourseCode = queue.poll();
            courseCodesOfEligibleModules.add(curCourseCode);

            List<String> nextModules = outDegree.get(curCourseCode);

            if (nextModules != null && !nextModules.isEmpty()) {
                for (String nextModule : nextModules) {
                    List<Set<String>> prereqGroups = inDegree.get(nextModule);

                    for (Set<String> prereqGroup : prereqGroups) {
                        prereqGroup.remove(curCourseCode);

                        if (prereqGroup.isEmpty()) {
                            queue.add(nextModule);
                            inDegree.put(nextModule, new ArrayList<>());
                            break;
                        }
                    }
                }
            }
        }

        return courseCodesOfEligibleModules;
    }
}