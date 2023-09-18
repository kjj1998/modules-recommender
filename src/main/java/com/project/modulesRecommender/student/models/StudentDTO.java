package com.project.modulesRecommender.student.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO {
    private String studentId;
    private String email;
    private String major;
    private String firstName;
    private String lastName;
    private Integer yearOfStudy;
    private List<String> courseCodes;
    private List<String> disciplines;
}
