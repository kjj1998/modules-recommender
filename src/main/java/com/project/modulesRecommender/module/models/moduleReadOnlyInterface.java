package com.project.modulesRecommender.module.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

public interface moduleReadOnlyInterface {

    @Data
    @Builder
    @AllArgsConstructor
    class ModuleRead {
        public final String courseCode;
        public final String courseName;
        public final String courseInformation;
        public final Integer academicUnits;
        public final Double score;
        public final Integer total;
    }

    @Data
    @Builder
    @AllArgsConstructor
    class ModuleResult {
        public final String courseCode;
        public final String courseName;
        public final String courseInformation;
        public final Integer academicUnits;
        public final Boolean broadeningAndDeepeningElective;
        public final String faculty;
        public final String gradeType;
        public final List<String> topics;
        public List<List<String>> prerequisites;
    }


    @Transactional(readOnly = true)
    Collection<com.project.modulesRecommender.module.models.ModuleRead> searchForModules(String searchTerm, Integer skip, Integer limit);

    @Transactional(readOnly = true)
    Collection<com.project.modulesRecommender.module.models.ModuleRead> retrieveAllModules(int skip, int limit);

    @Transactional(readOnly = true)
    com.project.modulesRecommender.module.models.ModuleRead retrieveModule(String courseCode);
}
