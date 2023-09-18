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
        Collection<ModuleRead> recModulesWithPrereqsFulfilledCF = getRecommendedModulesFromCFThatFulFillPrereqs(studentId);
        Collection<ModuleRead> recModulesWithNoPrereqsCF = getRecommendedModulesFromCFWithNoPrereqs(studentId);
        List<ModuleRead> allRecModulesCBF = new ArrayList<>(
                Stream.concat(recModulesWithPrereqsFulfilled.stream(), recModulesWithNoPrereqs.stream()).toList()
        );
        List<ModuleRead> allRecModulesCf = new ArrayList<>(
                Stream.concat(recModulesWithPrereqsFulfilledCF.stream(), recModulesWithNoPrereqsCF.stream()).toList()
        );

        allRecModulesCBF.sort(Comparator.comparingDouble(ModuleRead::getScore).reversed());
        var top10CbfModules = allRecModulesCBF.stream().limit(10).toList();
        List<ModuleRead> mergedRecs = merge(top10CbfModules, allRecModulesCf);

        Collections.shuffle(mergedRecs);

        return mergedRecs;   // Return top 20 module recommendations sorted by score in descending order

//        return recModulesWithNoPrereqsCF;

    }

    private List<ModuleRead> merge(List<ModuleRead> recModulesCbf, List<ModuleRead> recModulesCf) {
        Set<ModuleRead> set = new HashSet<>(recModulesCbf);
        set.addAll(recModulesCf);

        return set.stream().toList();
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
                        "AND rec.broadening_and_deepening = true " +
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
        var modules =  this.neo4jClient
                .query("MATCH (s:Student)-[t:TAKES]->(m:Module) " +
                        "WHERE s.student_id = $studentId " +
                        "MATCH (m)-[sim:SIMILAR]->(rec:Module {community: m.community}) " +
                        "WHERE NOT (rec)<-[:MUTUALLY_EXCLUSIVE]->(m) AND sim.score < 0.9 " +
                        "MATCH (rec)<-[:ARE_PREREQUISITES]-(prereq_group:PrerequisiteGroup)<-[:INSIDE]-(prereq:Module) " +
                        "MATCH (s:Student)-[t:TAKES]->(prereq:Module) " +
                        "WHERE rec.broadening_and_deepening = true " +
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

        return modules;
    }

    private Collection<ModuleRead> getRecommendedModulesFromCFWithNoPrereqs(String studentId) {
        return this.neo4jClient
                .query("MATCH (s1:Student)-[r:SIMILAR_TO_USER]->(s2:Student) " +
                        "WHERE s1.student_id = $studentId " +
                        "WITH s2.student_id AS neighborId, s1, r.jaccard_index AS score " +
                        "ORDER BY score DESC, neighborId " +
                        "WITH s1, COLLECT(neighborId)[0..10] as neighbours " +
                        "UNWIND neighbours AS neighborId " +
                        "WITH s1, neighborId " +
                        "MATCH (s3:Student)-[:TAKES]->(m:Module) " +
                        "WHERE s3.student_id = neighborId AND NOT (s1)-[:TAKES]->(m) " +
                        "WITH m AS coursesNotTaken, s1 " +
                        "WHERE coursesNotTaken.faculty <> 'ICC' " +
                        "AND NOT EXISTS { MATCH (coursesNotTaken)<-[:ARE_PREREQUISITES]-(:PrerequisiteGroup)<-[:INSIDE]-(:Module) } " +
                        "WITH DISTINCT coursesNotTaken " +
                        "RETURN coursesNotTaken.course_code AS course_code, coursesNotTaken.course_name AS course_name, " +
                        "coursesNotTaken.course_info AS course_info, coursesNotTaken.faculty AS faculty, " +
                        "coursesNotTaken.academic_units AS academic_units, coursesNotTaken.grade_type AS grade_type, " +
                        "coursesNotTaken.broadening_and_deepening AS bde")
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
                    var bde = Boolean.valueOf(String.valueOf(record.get("bde")).replaceAll("\"", ""));
                    var faculty = String.valueOf(record.get("faculty")).replaceAll("\"", "");
                    var gradeType = String.valueOf(record.get("grade_type")).replaceAll("\"", "");

                    return ModuleRead.builder()
                            .courseCode(courseCode)
                            .courseName(courseName)
                            .courseInformation(courseInfo)
                            .academicUnits(au)
                            .broadeningAndDeepeningElective(bde)
                            .faculty(faculty)
                            .gradeType(gradeType)
                            .build();
                }))
                .all();
    }

    private Collection<ModuleRead> getRecommendedModulesFromCFThatFulFillPrereqs(String studentId) {
        return this.neo4jClient
                .query("MATCH (s1:Student)-[r:SIMILAR_TO_USER]->(s2:Student) " +
                        "WHERE s1.student_id = $studentId " +
                        "WITH s2.student_id AS neighborId, s1, r.jaccard_index AS score " +
                        "ORDER BY score DESC, neighborId " +
                        "WITH s1, COLLECT(neighborId)[0..10] as neighbours " +
                        "UNWIND neighbours AS neighborId " +
                        "WITH s1, neighborId " +
                        "MATCH (s3:Student)-[:TAKES]->(m:Module) " +
                        "WHERE s3.student_id = neighborId AND NOT (s1)-[:TAKES]->(m) " +
                        "WITH m AS coursesNotTaken, s1 " +
                        "WHERE coursesNotTaken.faculty <> 'ICC' " +
                        "WITH DISTINCT coursesNotTaken, s1 " +
                        "MATCH (coursesNotTaken)<-[:ARE_PREREQUISITES]-(prereq_group:PrerequisiteGroup)<-[:INSIDE]-(prereq:Module) " +
                        "MATCH (s1)-[t:TAKES]->(prereq:Module) " +
                        "RETURN coursesNotTaken.course_code AS course_code, coursesNotTaken.course_name AS course_name, " +
                        "coursesNotTaken.course_info AS course_info, coursesNotTaken.faculty AS faculty, " +
                        "coursesNotTaken.academic_units AS academic_units, coursesNotTaken.grade_type AS grade_type, " +
                        "coursesNotTaken.broadening_and_deepening AS bde")
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
                    var bde = Boolean.valueOf(String.valueOf(record.get("bde")).replaceAll("\"", ""));
                    var faculty = String.valueOf(record.get("faculty")).replaceAll("\"", "");
                    var gradeType = String.valueOf(record.get("grade_type")).replaceAll("\"", "");

                    return ModuleRead.builder()
                            .courseCode(courseCode)
                            .courseName(courseName)
                            .courseInformation(courseInfo)
                            .academicUnits(au)
                            .broadeningAndDeepeningElective(bde)
                            .faculty(faculty)
                            .gradeType(gradeType)
                            .build();
                }))
                .all();
    }
}
