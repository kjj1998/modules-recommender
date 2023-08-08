package com.project.modulesRecommender.module.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class ModuleRead {
    public final String courseCode;
    public final String courseName;
    public final String courseInformation;
    public final Integer academicUnits;
    public final Boolean broadeningAndDeepeningElective;
    public final String faculty;
    public final String gradeType;
    public final Double score;
    public final Integer total;
    public final List<String> topics;
    public List<List<String>> prerequisites;
}
