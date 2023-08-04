package com.project.modulesRecommender.student;

import com.project.modulesRecommender.models.PrerequisiteGroup;
import com.project.modulesRecommender.module.Module;
import com.project.modulesRecommender.repositories.ModuleRepository;
import com.project.modulesRecommender.repositories.StudentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;
    @Mock
    private ModuleRepository moduleRepository;
    private final StudentService service = new StudentService(studentRepository, moduleRepository);

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
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
}