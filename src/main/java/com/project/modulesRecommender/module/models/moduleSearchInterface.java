package com.project.modulesRecommender.module.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

public interface moduleSearchInterface {

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

    @Transactional(readOnly = true)
    Collection<SearchResult> searchForModules(String searchTerm, Integer skip, Integer limit);

    @Transactional(readOnly = true)
    Collection<SearchResult> recommendModules(String studentId);
}
