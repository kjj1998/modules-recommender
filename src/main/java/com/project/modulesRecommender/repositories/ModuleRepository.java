package com.project.modulesRecommender.repositories;

import com.project.modulesRecommender.module.models.Module;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(exported = false)
public interface ModuleRepository extends Repository<Module, String> {
    Optional<Module> findById(String courseCode);

    @Query("MATCH (s:Student)-[t:TAKES]->(m:Module) WHERE s.student_id = $studentId WITH s, m MATCH (m)-[sim:SIMILAR]->(rec:Module {community: m.community}) MATCH (rec) WHERE NOT (rec)-[:MUTUALLY_EXCLUSIVE]->(m) AND sim.score <> 1.0 MATCH (rec)<-[:ARE_PREREQUISITES]-(prereq_group:PrerequisiteGroup)<-[:INSIDE]-(prereq:Module) MATCH (s:Student)-[t:TAKES]->(prereq:Module) WITH rec AS course, rec.course_name AS course_name, rec.course_code AS course_code, sim.score AS score ORDER BY score DESC MATCH (course)<-[:ARE_PREREQUISITES]-(prereq_group:PrerequisiteGroup)<-[:INSIDE]-(prereq:Module) MATCH (course)-[:CONTAIN]->(topic:Topic) RETURN course, prereq_group, topic, prereq")
    List<Module> recommend(@Param("studentId") String studentId);
}
