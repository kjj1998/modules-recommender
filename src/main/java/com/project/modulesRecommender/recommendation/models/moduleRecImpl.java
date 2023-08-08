package com.project.modulesRecommender.recommendation.models;

import com.project.modulesRecommender.module.models.ModuleRead;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Stream;

@Data
@Builder
@AllArgsConstructor
@Component
public class moduleRecImpl implements moduleRecInterface {

    private final Neo4jClient neo4jClient;

    @Override
    public Collection<ModuleRead> recommendModules(String studentId) {

        Collection<ModuleRead> recModulesWithPrereqsFulfilled = getRecommendedModulesThatFulfillPrereqs(studentId);
        Collection<ModuleRead> recModulesWithNoPrereqs = getRecommendedModulesWithNoPrereqs(studentId);
        List<ModuleRead> allRecModules =
                new ArrayList<>(Stream.concat(recModulesWithPrereqsFulfilled.stream(), recModulesWithNoPrereqs.stream()).toList());

        allRecModules.sort(Comparator.comparingDouble(ModuleRead::getScore).reversed());

        return allRecModules.stream().limit(20).toList();   // Return top 20 module recommendations sorted by score in descending order
    }

    private Collection<ModuleRead> getRecommendedModulesWithNoPrereqs(String studentId) {
        return this.neo4jClient
                .query("MATCH (s:Student)-[t:TAKES]->(m:Module) " +
                        "WHERE s.student_id = $studentId " +
                        "MATCH (m)-[sim:SIMILAR]->(rec:Module {community: m.community}) " +
                        "WHERE NOT (rec)<-[:MUTUALLY_EXCLUSIVE]->(m) AND sim.score < 0.9 " +
                        "AND NOT EXISTS { " +
                        "  MATCH (rec)<-[:ARE_PREREQUISITES]-(:PrerequisiteGroup)<-[:INSIDE]-(:Module) " +
                        "}" +
                        "RETURN rec.course_code AS course_code, rec.course_name AS course_name, rec.course_info AS course_info, " +
                        "rec.academic_units AS academic_units, rec.broadening_and_deepening AS bde, rec.faculty AS faculty, " +
                        "rec.grade_type AS grade_type, sim.score AS score"
                )
                .bindAll(new HashMap<>() {
                    {
                        put("studentId", studentId);
                    }
                })
                .fetchAs(ModuleRead.class)
                .mappedBy(((typeSystem, record) -> {
                    var courseCode = String.valueOf(record.get("course_code")).replaceAll("\"", "");
                    var courseName = String.valueOf(record.get("course_name")).replaceAll("\"", "");
                    var courseInfo = String.valueOf(record.get("course_info")).replaceAll("\"", "");
                    var au = Integer.valueOf(String.valueOf(record.get("academic_units")));
                    var score = Double.valueOf(String.valueOf(record.get("score")));
                    var bde = Boolean.valueOf(String.valueOf(record.get("bde")).replaceAll("\"", ""));
                    var faculty = String.valueOf(record.get("faculty")).replaceAll("\"", "");
                    var gradeType = String.valueOf(record.get("grade_type")).replaceAll("\"", "");

                    return ModuleRead.builder()
                            .courseCode(courseCode)
                            .courseName(courseName)
                            .courseInformation(courseInfo)
                            .academicUnits(au)
                            .score(score)
                            .broadeningAndDeepeningElective(bde)
                            .faculty(faculty)
                            .gradeType(gradeType)
                            .build();
                }))
                .all();
    }

    private Collection<ModuleRead> getRecommendedModulesThatFulfillPrereqs(String studentId) {
        return this.neo4jClient
                .query("MATCH (s:Student)-[t:TAKES]->(m:Module) " +
                        "WHERE s.student_id = $studentId " +
                        "MATCH (m)-[sim:SIMILAR]->(rec:Module {community: m.community}) " +
                        "WHERE NOT (rec)<-[:MUTUALLY_EXCLUSIVE]->(m) AND sim.score < 0.9 " +
                        "MATCH (rec)<-[:ARE_PREREQUISITES]-(prereq_group:PrerequisiteGroup)<-[:INSIDE]-(prereq:Module) " +
                        "MATCH (s:Student)-[t:TAKES]->(prereq:Module) " +
                        "RETURN rec.course_code AS course_code, rec.course_name AS course_name, rec.course_info AS course_info, " +
                        "rec.faculty AS faculty, rec.academic_units AS academic_units, rec.grade_type AS grade_type, " +
                        "rec.broadening_and_deepening AS bde, sim.score AS score")
                .bindAll(new HashMap<>() {
                    {
                        put("studentId", studentId);
                    }
                })
                .fetchAs(ModuleRead.class)
                .mappedBy(((typeSystem, record) -> {
                    var courseCode = String.valueOf(record.get("course_code")).replaceAll("\"", "");
                    var courseName = String.valueOf(record.get("course_name")).replaceAll("\"", "");
                    var courseInfo = String.valueOf(record.get("course_info")).replaceAll("\"", "");
                    var au = Integer.valueOf(String.valueOf(record.get("academic_units")));
                    var score = Double.valueOf(String.valueOf(record.get("score")));
                    var bde = Boolean.valueOf(String.valueOf(record.get("bde")).replaceAll("\"", ""));
                    var faculty = String.valueOf(record.get("faculty")).replaceAll("\"", "");
                    var gradeType = String.valueOf(record.get("grade_type")).replaceAll("\"", "");

                    return ModuleRead.builder()
                            .courseCode(courseCode)
                            .courseName(courseName)
                            .courseInformation(courseInfo)
                            .academicUnits(au)
                            .score(score)
                            .broadeningAndDeepeningElective(bde)
                            .faculty(faculty)
                            .gradeType(gradeType)
                            .build();
                }))
                .all();
    }
}
