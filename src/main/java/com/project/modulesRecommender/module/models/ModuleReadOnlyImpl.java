package com.project.modulesRecommender.module.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@Component
public class ModuleReadOnlyImpl implements moduleReadOnlyInterface {
    private final Neo4jClient neo4jClient;

    @Override
    public Collection<com.project.modulesRecommender.module.models.ModuleRead> searchForModules(String searchTerm, Integer skip, Integer limit) {
        String searchTermWithWildCards = '*' + searchTerm + '*';

        return this.neo4jClient
                .query("CALL db.index.fulltext.queryNodes('moduleIndex', $searchTerm) YIELD node, score " +
                        "WITH COUNT(*) AS total " +
                        "CALL db.index.fulltext.queryNodes('moduleIndex', $searchTerm) YIELD node, score " +
                        "RETURN node.course_code AS course_code, node.course_name AS course_name, node.course_info AS course_info, " +
                        "node.academic_units AS au, node.faculty AS faculty, node.grade_type AS grade_type, score, total " +
                        "SKIP $skip LIMIT $limit")
                .bindAll(new HashMap<>() {
                    {
                        put("searchTerm", searchTermWithWildCards);
                        put("skip", skip);
                        put("limit", limit);
                    }
                })
                .fetchAs(com.project.modulesRecommender.module.models.ModuleRead.class)
                .mappedBy(((typeSystem, record) -> {
                    var courseCode = String.valueOf(record.get("course_code")).replaceAll("\"", "");
                    var courseName = String.valueOf(record.get("course_name")).replaceAll("\"", "");
                    var courseInfo = String.valueOf(record.get("course_info")).replaceAll("\"", "");
                    var au = Integer.valueOf(String.valueOf(record.get("au")));
                    var score = Double.valueOf(String.valueOf(record.get("score")));
                    var total = Integer.valueOf(String.valueOf(record.get("total")));
                    var bde = Boolean.valueOf(String.valueOf(record.get("bde")).replaceAll("\"", ""));
                    var faculty = String.valueOf(record.get("faculty")).replaceAll("\"", "");
                    var gradeType = String.valueOf(record.get("grade_type")).replaceAll("\"", "");

                    return com.project.modulesRecommender.module.models.ModuleRead.builder()
                            .courseCode(courseCode)
                            .courseName(courseName)
                            .courseInformation(courseInfo)
                            .faculty(faculty)
                            .academicUnits(au)
                            .broadeningAndDeepeningElective(bde)
                            .gradeType(gradeType)
                            .score(score)
                            .total(total)
                            .build();
                }))
                .all();
    }

    @Override
    public Collection<com.project.modulesRecommender.module.models.ModuleRead> retrieveAllModules(int skip, int limit) {
        List<com.project.modulesRecommender.module.models.ModuleRead> modules = this.neo4jClient
                .query("MATCH (m:Module) " +
                        "WITH COUNT(m) AS total " +
                        "MATCH (m:Module) " +
                        "RETURN m.course_code AS course_code, m.course_name AS course_name, m.course_info AS course_info, " +
                        "m.faculty AS faculty, m.academic_units AS au, m.broadening_and_deepening AS bde, m.grade_type AS grade_type, total " +
                        "SKIP $skip LIMIT $limit")
                .bindAll(new HashMap<>() {
                    {
                        put("skip", skip);
                        put("limit", limit);
                    }
                })
                .fetchAs(com.project.modulesRecommender.module.models.ModuleRead.class)
                .mappedBy(((typeSystem, record) -> {
                    var courseCode = String.valueOf(record.get("course_code")).replaceAll("\"", "");
                    var courseName = String.valueOf(record.get("course_name")).replaceAll("\"", "");
                    var courseInfo = String.valueOf(record.get("course_info")).replaceAll("\"", "");
                    var au = Integer.valueOf(String.valueOf(record.get("au")));
                    var total = Integer.valueOf(String.valueOf(record.get("total")));
                    var bde = Boolean.valueOf(String.valueOf(record.get("bde")).replaceAll("\"", ""));
                    var faculty = String.valueOf(record.get("faculty")).replaceAll("\"", "");
                    var gradeType = String.valueOf(record.get("grade_type")).replaceAll("\"", "");

                    return com.project.modulesRecommender.module.models.ModuleRead.builder()
                            .courseCode(courseCode)
                            .courseName(courseName)
                            .courseInformation(courseInfo)
                            .faculty(faculty)
                            .academicUnits(au)
                            .broadeningAndDeepeningElective(bde)
                            .gradeType(gradeType)
                            .score(0.0)
                            .total(total)
                            .build();
                }))
                .all().stream().toList();

        List<String> courseCodes = modules.stream().map(com.project.modulesRecommender.module.models.ModuleRead::getCourseCode).toList();

        for (int i = 0; i < courseCodes.size(); i++) {
            modules.get(i).prerequisites = getPrereqGroupsForEachModule(courseCodes.get(i));
        }

        return modules;
    }


    @Override
    public com.project.modulesRecommender.module.models.ModuleRead retrieveModule(String courseCode) {
        var module =  this.neo4jClient
                .query("MATCH (topic:Topic)<-[:CONTAIN]-(m:Module) " +
                        "WHERE m.course_code = $courseCode " +
                        "RETURN m.course_code AS course_code, m.course_info AS course_info, m.broadening_and_deepening AS bde, " +
                        "m.course_name AS course_name, m.academic_units AS au, m.faculty AS faculty, m.grade_type AS grade_type, " +
                        "COLLECT(DISTINCT topic.name) AS topics")
                .bindAll(new HashMap<>() {
                    {
                        put("courseCode", courseCode);
                    }
                })
                .fetchAs(com.project.modulesRecommender.module.models.ModuleRead.class)
                .mappedBy(((typeSystem, record) -> {
                    var courseName = String.valueOf(record.get("course_name")).replaceAll("\"", "");
                    var courseInfo = String.valueOf(record.get("course_info")).replaceAll("\"", "");
                    var bde = Boolean.valueOf(String.valueOf(record.get("bde")).replaceAll("\"", ""));
                    var au = Integer.valueOf(String.valueOf(record.get("au")));
                    var faculty = String.valueOf(record.get("faculty")).replaceAll("\"", "");
                    var grade_type = String.valueOf(record.get("grade_type")).replaceAll("\"", "");


                    return com.project.modulesRecommender.module.models.ModuleRead.builder()
                            .courseCode(courseCode)
                            .courseName(courseName)
                            .courseInformation(courseInfo)
                            .academicUnits(au)
                            .broadeningAndDeepeningElective(bde)
                            .faculty(faculty)
                            .gradeType(grade_type)
                            .build();
                }))
                .one().get();

        module.prerequisites = getPrereqGroupsForEachModule(courseCode);

        return module;
    }

    private List<List<String>> getPrereqGroupsForEachModule(String courseCode) {
        var collectionOfPrereqGroups = this.neo4jClient
                .query("MATCH (m:Module)<-[:ARE_PREREQUISITES]-(pg:PrerequisiteGroup)<-[:INSIDE]-(prereq:Module) " +
                        "WHERE m.course_code = $courseCode " +
                        "RETURN pg.group_id AS groupIds, COLLECT(DISTINCT prereq.course_code) AS prereqInfo")
                .bindAll(new HashMap<>() {
                    {
                        put("courseCode", courseCode);
                    }
                })
                .fetchAs(List.class)
                .mappedBy(((typeSystem, record) -> record.get("prereqInfo").asList()))
                .all();

        List<List<String>> listOfPrereqGroups = new ArrayList<>();

        collectionOfPrereqGroups.forEach(prereqGroup -> {
            List<String> p = new ArrayList<>();
            for (Object o : prereqGroup) {
                p.add(o.toString());
            }
            listOfPrereqGroups.add(p);
        });
        return listOfPrereqGroups;
    }
}
