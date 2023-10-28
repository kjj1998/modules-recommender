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
    public RecommendationsDTO recommendModules(String studentId) {
        Collection<ModuleRead> cbfRecsWithPrereqsFulfilled = getRecommendedModulesFromCbfThatFulfillPrereqs(studentId);
        Collection<ModuleRead> cbfRecsWithNoPrereqs = getRecommendedModulesFromCbfWithNoPrereqs(studentId);

        Collection<ModuleRead> cfRecsWithPrereqsFulfilled = getRecommendedModulesFromCfThatFulFillPrereqs(studentId);
        Collection<ModuleRead> cfRecsWithNoPrereqs = getRecommendedModulesFromCfWithNoPrereqs(studentId);

        List<ModuleRead> cbfRecs = new ArrayList<>(
                Stream.concat(cbfRecsWithPrereqsFulfilled.stream(), cbfRecsWithNoPrereqs.stream()).toList());
        List<ModuleRead> cfRecs = new ArrayList<>(
                Stream.concat(cfRecsWithPrereqsFulfilled.stream(), cfRecsWithNoPrereqs.stream()).toList());

        cbfRecs.sort(Comparator.comparingDouble(ModuleRead::getScore).reversed());
//        Collections.shuffle(cbfRecs);
        var top10CbfModules = cbfRecs.stream().limit(10).toList();
        var top10CfModules = cfRecs.stream().limit(10).toList();


        return RecommendationsDTO.builder()
                .cfRecommendations(top10CfModules)
                .cbfRecommendations(top10CbfModules)
                .build();
    }

    private List<ModuleRead> merge(List<ModuleRead> recModulesCbf, List<ModuleRead> recModulesCf) {
        Set<ModuleRead> set = new HashSet<>(recModulesCbf);
        set.addAll(recModulesCf);

        return set.stream().toList();
    }

    private Collection<ModuleRead> getRecommendedModulesFromCbfWithNoPrereqs(String studentId) {
        return this.neo4jClient
                .query("MATCH (s:Student)-[t:TAKES]->(m:Module) " +
                        "WHERE s.student_id = $studentId " +
                        "MATCH (m)-[sim:SIMILAR]->(rec:Module {community: m.community}) " +
                        "WHERE NOT (rec)<-[:MUTUALLY_EXCLUSIVE]->(m) AND sim.score < 0.85 " +
                        "AND NOT EXISTS { " +
                        "  MATCH (rec)<-[:ARE_PREREQUISITES]-(:PrerequisiteGroup)<-[:INSIDE]-(:Module) " +
                        "}" +
                        "AND rec.broadening_and_deepening = true " +
                        "WITH s, rec, sim " +
                        "ORDER BY sim.score DESC " +
                        "UNWIND s.disciplines AS disciplines " +
                        "MATCH (filteredRec: Module { course_code: rec.course_code}) " +
                        "WHERE filteredRec.discipline <> disciplines AND filteredRec.discipline <> 'Interdisciplinary Collaborative Core' " +
                        "AND filteredRec.discipline <> 'CN Yang Scholars Programme' AND filteredRec.discipline <> 'University Scholars Programme' " +
                        "AND filteredRec.discipline <> 'Renaissance Engineering' " +
                        "RETURN filteredRec.course_code AS course_code, filteredRec.course_name AS course_name, " +
                        "filteredRec.course_info AS course_info, filteredRec.academic_units AS academic_units, " +
                        "filteredRec.broadening_and_deepening AS bde, filteredRec.faculty AS faculty, " +
                        "filteredRec.grade_type AS grade_type, sim.score AS score LIMIT 10"
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

    private Collection<ModuleRead> getRecommendedModulesFromCbfThatFulfillPrereqs(String studentId) {

        return this.neo4jClient.query(
                "MATCH (s:Student)-[t:TAKES]->(m:Module) " +
                        "WHERE s.student_id = $studentId " +
                        "MATCH (m)-[sim:SIMILAR]->(rec:Module {community: m.community}) " +
                        "WHERE NOT (rec)<-[:MUTUALLY_EXCLUSIVE]->(m) AND sim.score < 0.85 " +
                        "MATCH (rec)<-[:ARE_PREREQUISITES]-(prereq_group:PrerequisiteGroup)<-[:INSIDE]-(prereq:Module) " +
                        "MATCH (s:Student)-[t:TAKES]->(prereq:Module) " +
                        "WHERE rec.broadening_and_deepening = true " +
                        "RETURN rec.course_code AS course_code, rec.course_name AS course_name, rec.course_info AS course_info, " +
                        "rec.faculty AS faculty, rec.academic_units AS academic_units, rec.grade_type AS grade_type, " +
                        "rec.broadening_and_deepening AS bde, sim.score AS score LIMIT 10")
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

    private Collection<ModuleRead> getRecommendedModulesFromCfWithNoPrereqs(String studentId) {
        return this.neo4jClient.query(
                "MATCH (s1:Student)-[r:SIMILAR_TO_USER]->(s2:Student) " +
                        "WHERE s1.student_id = $studentId " +
                        "WITH s2.student_id AS neighborId, s1, r.jaccard_index AS score " +
                        "ORDER BY score DESC, neighborId " +
                        "WITH s1, COLLECT(neighborId)[0..10] as neighbours " +
                        "UNWIND neighbours AS neighborId " +
                        "WITH s1, neighborId " +
                        "MATCH (s3:Student)-[:TAKES]->(m:Module) " +
                        "WHERE s3.student_id = neighborId AND NOT (s1)-[:TAKES]->(m) " +
                        "WITH m AS coursesNotTaken, s1 " +
                        "UNWIND s1.disciplines AS disciplines " +
                        "WITH coursesNotTaken, disciplines " +
                        "MATCH (coursesNotTakenFiltered:Module {course_code: coursesNotTaken.course_code}) " +
                        "WHERE coursesNotTaken.discipline <> disciplines AND coursesNotTaken.discipline <> 'Interdisciplinary Collaborative Core' " +
                        "AND coursesNotTaken.discipline <> 'CN Yang Scholars Programme' AND coursesNotTaken.discipline <> 'University Scholars Programme' " +
                        "AND NOT EXISTS { MATCH (coursesNotTaken)<-[:ARE_PREREQUISITES]-(:PrerequisiteGroup)<-[:INSIDE]-(:Module) } " +
                        "WITH COUNT(DISTINCT coursesNotTakenFiltered.course_code) AS cnt, coursesNotTakenFiltered " +
                        "ORDER BY cnt DESC " +
                        "WITH DISTINCT coursesNotTakenFiltered " +
                        "RETURN coursesNotTakenFiltered.course_code AS course_code, coursesNotTakenFiltered.course_name AS course_name, " +
                        "coursesNotTakenFiltered.course_info AS course_info, coursesNotTakenFiltered.faculty AS faculty, " +
                        "coursesNotTakenFiltered.academic_units AS academic_units, coursesNotTakenFiltered.grade_type AS grade_type, " +
                        "coursesNotTakenFiltered.broadening_and_deepening AS bde")
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

    private Collection<ModuleRead> getRecommendedModulesFromCfThatFulFillPrereqs(String studentId) {
        return this.neo4jClient.query(
                "MATCH (s1:Student)-[r:SIMILAR_TO_USER]->(s2:Student) " +
                        "WHERE s1.student_id = $studentId " +
                        "WITH s2.student_id AS neighborId, s1, r.jaccard_index AS score " +
                        "ORDER BY score DESC, neighborId " +
                        "WITH s1, COLLECT(neighborId)[0..10] as neighbours " +
                        "UNWIND neighbours AS neighborId " +
                        "WITH s1, neighborId " +
                        "MATCH (s3:Student)-[:TAKES]->(m:Module) " +
                        "WHERE s3.student_id = neighborId AND NOT (s1)-[:TAKES]->(m) " +
                        "WITH m AS coursesNotTaken, s1 " +
                        "UNWIND s1.disciplines AS disciplines " +
                        "WITH coursesNotTaken, disciplines, s1 " +
                        "MATCH (coursesNotTakenFiltered:Module {course_code: coursesNotTaken.course_code}) " +
                        "WHERE coursesNotTaken.discipline <> disciplines AND coursesNotTaken.discipline <> 'Interdisciplinary Collaborative Core' " +
                        "AND coursesNotTaken.discipline <> 'CN Yang Scholars Programme' AND coursesNotTaken.discipline <> 'University Scholars Programme' " +
                        "WITH DISTINCT coursesNotTakenFiltered, s1 " +
                        "MATCH (coursesNotTakenFiltered)<-[:ARE_PREREQUISITES]-(prereq_group:PrerequisiteGroup)<-[:INSIDE]-(prereq:Module) " +
                        "MATCH (s1)-[t:TAKES]->(prereq:Module) " +
                        "WITH COUNT(distinct coursesNotTakenFiltered.course_code) AS cnt, coursesNotTakenFiltered " +
                        "ORDER BY cnt DESC " +
                        "WITH DISTINCT coursesNotTakenFiltered " +
                        "RETURN coursesNotTakenFiltered.course_code AS course_code, coursesNotTakenFiltered.course_name AS course_name, " +
                        "coursesNotTakenFiltered.course_info AS course_info, coursesNotTakenFiltered.faculty AS faculty, " +
                        "coursesNotTakenFiltered.academic_units AS academic_units, coursesNotTakenFiltered.grade_type AS grade_type, " +
                        "coursesNotTakenFiltered.broadening_and_deepening AS bde")
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
