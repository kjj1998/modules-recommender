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
    class SearchResult {
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
    Collection<SearchResult> searchForModules(String searchTerm, Integer skip, Integer limit);

    @Transactional(readOnly = true)
    Collection<ModuleRead> retrieveAllModules(int skip, int limit);

    @Transactional(readOnly = true)
    ModuleRead retrieveModule(String courseCode);
}
