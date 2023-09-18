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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((courseCode == null) ? 0 : courseCode.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final ModuleRead other = (ModuleRead) obj;
        return courseCode.equals(other.courseCode);
    }
}
