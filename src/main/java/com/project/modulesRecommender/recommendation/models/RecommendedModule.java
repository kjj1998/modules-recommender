package com.project.modulesRecommender.recommendation.models;

import com.project.modulesRecommender.recommendation.models.moduleRecInterface;
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
public class RecommendedModule implements moduleRecInterface {

    private final Neo4jClient neo4jClient;

    @Override
    public Collection<Recommendation> recommendModules(String studentId) {

        return this.neo4jClient
                .query("MATCH (s:Student)-[t:TAKES]->(m:Module) " +
                        "WHERE s.student_id = $studentId " +
                        "MATCH (m)-[sim:SIMILAR]->(rec:Module {community: m.community}) " +
                        "WHERE NOT (rec)-[:MUTUALLY_EXCLUSIVE]->(m) AND sim.score <> 1.0 " +
                        "MATCH (rec)<-[:ARE_PREREQUISITES]-(prereq_group:PrerequisiteGroup)<-[:INSIDE]-(prereq:Module) " +
                        "MATCH (s)-[t]->(prereq:Module) " +
                        "WITH rec.course_name AS course_name, rec.course_code AS course_code, rec.academic_units AS academic_units, rec.course_information as course_info, sim.score AS score ORDER BY score DESC " +
                        "RETURN course_code, course_name, course_info, academic_units, score")
                .bindAll(new HashMap<>() {
                    {
                        put("studentId", studentId);
                    }
                })
                .fetchAs(Recommendation.class)
                .mappedBy(((typeSystem, record) -> {
                    var courseCode = String.valueOf(record.get("course_code")).replaceAll("\"", "");
                    var courseName = String.valueOf(record.get("course_name")).replaceAll("\"", "");
                    var courseInfo = String.valueOf(record.get("course_info")).replaceAll("\"", "");
                    var au = Integer.valueOf(String.valueOf(record.get("academic_units")));
                    var score = Double.valueOf(String.valueOf(record.get("score")));

                    return Recommendation
                            .builder()
                            .courseCode(courseCode)
                            .courseName(courseName)
                            .courseInformation(courseInfo)
                            .academicUnits(au)
                            .score(score)
                            .total(record.keys().size())
                            .build();
                }))
                .all();
    }
}
