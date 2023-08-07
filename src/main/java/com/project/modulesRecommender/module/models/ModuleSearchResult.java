package com.project.modulesRecommender.module.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;

@Data
@Builder
@AllArgsConstructor
@Component
public class ModuleSearchResult implements moduleSearchInterface {
    private final Neo4jClient neo4jClient;

    @Override
    public Collection<SearchResult> searchForModules(String searchTerm, Integer skip, Integer limit) {
        return this.neo4jClient
                .query("CALL db.index.fulltext.queryNodes('moduleIndex', $searchTerm) YIELD node, score " +
                        "WITH COUNT(*) AS total " +
                        "CALL db.index.fulltext.queryNodes('moduleIndex', $searchTerm) YIELD node, score " +
                        "RETURN node.course_code AS course_code, node.course_name AS course_name, node.course_information AS course_info, node.academic_units AS au, score, total " +
                        "SKIP $skip LIMIT $limit")
                .bindAll(new HashMap<>() {
                    {
                        put("searchTerm", searchTerm);
                        put("skip", skip);
                        put("limit", limit);
                    }
                })
                .fetchAs(SearchResult.class)
                .mappedBy(((typeSystem, record) -> {
                    var courseCode = String.valueOf(record.get("course_code")).replaceAll("\"", "");
                    var courseName = String.valueOf(record.get("course_name")).replaceAll("\"", "");
                    var courseInfo = String.valueOf(record.get("course_info")).replaceAll("\"", "");
                    var au = Integer.valueOf(String.valueOf(record.get("au")));
                    var score = Double.valueOf(String.valueOf(record.get("score")));
                    var total = Integer.valueOf(String.valueOf(record.get("total")));

                    return SearchResult
                            .builder()
                            .courseCode(courseCode)
                            .courseName(courseName)
                            .courseInformation(courseInfo)
                            .academicUnits(au)
                            .score(score)
                            .total(total)
                            .build();
                }))
                .all();
    }

    @Override
    public Collection<SearchResult> recommendModules(String studentId) {
        return null;
    }
}
