package com.project.modulesRecommender.student;

import com.project.modulesRecommender.module.models.PrerequisiteGroup;
import com.project.modulesRecommender.module.models.Module;
import com.project.modulesRecommender.repositories.ModuleRepository;
import com.project.modulesRecommender.repositories.StudentRepository;
import com.project.modulesRecommender.student.models.Student;
import com.project.modulesRecommender.student.models.StudentDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock private StudentRepository studentRepository;
    @Mock private ModuleRepository moduleRepository;
    private StudentService service;

    @BeforeEach
    void setUp() {
        service = new StudentService(studentRepository, moduleRepository);
    }

    @Test
    void check_Modules_Fulfill_Prerequisites_Success() {

        // Given
        var moduleA = Module.builder()
                .courseCode("A0001")
                .courseName("Module A")
                .prerequisites(null)
                .build();

        var moduleB = Module.builder()
                .courseCode("B0001")
                .courseName("Module B")
                .prerequisites(null)
                .build();

        var moduleC = Module.builder()
                .courseCode("C0001")
                .courseName("Module C")
                .prerequisites(null)
                .build();

        var prereqGrp1 = PrerequisiteGroup.builder()
                .groupId("PrereqGrp1")
                .modules(new ArrayList<>(Arrays.asList(moduleA.clone(), moduleB.clone())))
                .build();

        var prereqGrp2 = PrerequisiteGroup.builder()
                .groupId("PrereqGrp2")
                .modules(new ArrayList<>(Arrays.asList(moduleA.clone(), moduleC.clone())))
                .build();

        var moduleD = Module.builder()
                .courseCode("D0001")
                .courseName("Module D")
                .prerequisites(new ArrayList<>(Arrays.asList(prereqGrp1, prereqGrp2)))
                .build();

        var moduleE = Module.builder()
                .courseCode("E0001")
                .courseName("Module E")
                .prerequisites(new ArrayList<>(Collections.singletonList(prereqGrp2)))
                .build();

        var prereqGrp3 = PrerequisiteGroup.builder()
                .groupId("PrereqGrp3")
                .modules(new ArrayList<>(Arrays.asList(moduleD.clone(), moduleE.clone())))
                .build();

        var moduleF = Module.builder()
                .courseCode("F0001")
                .courseName("Module F")
                .prerequisites(new ArrayList<>(Collections.singletonList(prereqGrp3)))
                .build();

        List<Module> modules = new ArrayList<>(Arrays.asList(moduleA, moduleB, moduleC, moduleD, moduleE, moduleF));
        List<String> courseCodes = new ArrayList<>(Arrays.asList(
                moduleA.getCourseCode(), moduleB.getCourseCode(), moduleC.getCourseCode(),
                moduleD.getCourseCode(), moduleE.getCourseCode(), moduleF.getCourseCode()));

        // When
        var result = service.checkPrerequisitesFulfillment(modules);

        // Then
        assertThat(result.size()).isEqualTo(courseCodes.size());
    }

    @Test
    void check_Modules_Fulfill_Prerequisites_Failure() {

        // Given
        var moduleA = Module.builder()
                .courseCode("A0001")
                .courseName("Module A")
                .prerequisites(null)
                .build();

        var moduleB = Module.builder()
                .courseCode("B0001")
                .courseName("Module B")
                .prerequisites(null)
                .build();

        var moduleC = Module.builder()
                .courseCode("C0001")
                .courseName("Module C")
                .prerequisites(null)
                .build();

        var prereqGrp1 = PrerequisiteGroup.builder()
                .groupId("PrereqGrp1")
                .modules(new ArrayList<>(Arrays.asList(moduleA.clone(), moduleB.clone())))
                .build();

        var prereqGrp2 = PrerequisiteGroup.builder()
                .groupId("PrereqGrp2")
                .modules(new ArrayList<>(Arrays.asList(moduleA.clone(), moduleC.clone())))
                .build();

        var moduleD = Module.builder()
                .courseCode("D0001")
                .courseName("Module D")
                .prerequisites(new ArrayList<>(Arrays.asList(prereqGrp1, prereqGrp2)))
                .build();

        var moduleE = Module.builder()
                .courseCode("E0001")
                .courseName("Module E")
                .prerequisites(new ArrayList<>(Collections.singletonList(prereqGrp2)))
                .build();

        var prereqGrp3 = PrerequisiteGroup.builder()
                .groupId("PrereqGrp3")
                .modules(new ArrayList<>(Arrays.asList(moduleD.clone(), moduleE.clone())))
                .build();

        var moduleF = Module.builder()
                .courseCode("F0001")
                .courseName("Module F")
                .prerequisites(new ArrayList<>(Collections.singletonList(prereqGrp3)))
                .build();

        List<Module> modules = new ArrayList<>(Arrays.asList(moduleA, moduleB, moduleC, moduleD, moduleF));
        List<String> courseCodes = new ArrayList<>(Arrays.asList(
                moduleA.getCourseCode(), moduleB.getCourseCode(), moduleC.getCourseCode(),
                moduleD.getCourseCode(), moduleE.getCourseCode(), moduleF.getCourseCode()));

        // When
        var result = service.checkPrerequisitesFulfillment(modules);

        // Then
        assertThat(result.size()).isNotEqualTo(courseCodes.size());
    }

    @Test
    void canUpdateStudents() {
        // given
        var updatedStudentDto = StudentDTO.builder()
                .studentId("Abc1234")
                .email("student@school.com")
                .major("Engineering")
                .firstName("John")
                .lastName("Doe")
                .yearOfStudy(2)
                .courseCodes(new ArrayList<>() {{
                    add("CZ1001");
                }})
                .build();
        var updatedStudent = Student.builder()
                .studentId("Abc1234")
                .firstName("John")
                .lastName("Doe")
                .email("student@school.com")
                .major("Engineering")
                .yearOfStudy(2)
                .modules(new ArrayList<>() {{
                    add(Module.builder().courseCode("CZ1001").build());
                }})
                .build();

        given(studentRepository.existsById(updatedStudentDto.getStudentId())).willReturn(true);
        given(moduleRepository.findById("CZ1001")).willReturn(Optional.of(Module.builder().courseCode("CZ1001").build()));
        given(studentRepository.findById(updatedStudentDto.getStudentId())).willReturn(Optional.of(Student.builder().studentId("Abc1234").build()));
        when(studentRepository.save(any(Student.class))).thenReturn(updatedStudent);

        // when
        service.updateStudent(updatedStudentDto);

        // then
        ArgumentCaptor<Student> studentArgumentCaptor = ArgumentCaptor.forClass(Student.class);
        ArgumentCaptor<String> studentIdArgumentCaptor1 = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> studentIdArgumentCaptor2 = ArgumentCaptor.forClass(String.class);

        verify(studentRepository).save(studentArgumentCaptor.capture());
        verify(studentRepository).existsById(studentIdArgumentCaptor1.capture());
        verify(studentRepository).findById(studentIdArgumentCaptor2.capture());

        Student captureStudent = studentArgumentCaptor.getValue();
        String existsId = studentIdArgumentCaptor1.getValue();
        String findId = studentIdArgumentCaptor2.getValue();

        assertThat(captureStudent).isEqualTo(updatedStudent);
        assertThat(existsId).isEqualTo(updatedStudentDto.getStudentId());
        assertThat(findId).isEqualTo(updatedStudentDto.getStudentId());
    }
}