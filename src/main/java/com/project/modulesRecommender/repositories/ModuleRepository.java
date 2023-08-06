package com.project.modulesRecommender.repositories;

import com.project.modulesRecommender.module.Module;
import com.project.modulesRecommender.module.ModuleSearchResult;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RepositoryRestResource(exported = false)
public interface ModuleRepository extends Repository<Module, String> {
    Optional<Module> findById(String courseCode);

//    @Query("MATCH (s:Student)-[t:TAKES]->(m:Module) WHERE s.student_id = $studentId WITH s, m MATCH (m)-[sim:SIMILAR]->(rec:Module {community: m.community}) MATCH (rec) WHERE NOT (rec)-[:MUTUALLY_EXCLUSIVE]->(m) AND sim.score <> 1.0 MATCH (rec)<-[:ARE_PREREQUISITES]-(prereq_group:PrerequisiteGroup)<-[:INSIDE]-(prereq:Module) MATCH (s:Student)-[t:TAKES]->(prereq:Module) WITH rec AS course, rec.course_name AS course_name, rec.course_code AS course_code, sim.score AS score ORDER BY score DESC MATCH (course)<-[:ARE_PREREQUISITES]-(prereq_group:PrerequisiteGroup)<-[:INSIDE]-(prereq:Module) MATCH (course)-[:CONTAIN]->(topic:Topic) RETURN course, prereq_group, topic, prereq")
    @Query("MATCH (s:Student)-[t:TAKES]->(m:Module) WHERE s.student_id = $studentId WITH s, m OPTIONAL MATCH (m)-[sim:SIMILAR]->(rec:Module {community: m.community}) WITH sim, rec, m, s WHERE NOT (rec)-[:MUTUALLY_EXCLUSIVE]->(m) AND sim.score <> 1.0 with rec, s, sim OPTIONAL MATCH (rec)<-[:ARE_PREREQUISITES]-(prereq_group:PrerequisiteGroup)<-[:INSIDE]-(prereq:Module) WITH prereq, rec, s, sim OPTIONAL MATCH (s:Student)-[t:TAKES]->(prereq:Module) with rec, prereq, s, sim WITH rec.course_name AS course_name, rec.course_code AS course_code, sim.score AS score ORDER BY score DESC RETURN course_name, course_code, score")
    List<Module> recommend(@Param("studentId") String studentId);

    @Query("MATCH (s:Student)-[t:TAKES]->(m:Module) WHERE s.student_id = $studentId WITH s, m OPTIONAL MATCH (m)-[sim:SIMILAR]->(rec:Module {community: m.community}) WITH sim, rec, m, s WHERE NOT (rec)-[:MUTUALLY_EXCLUSIVE]->(m) AND sim.score <> 1.0 with rec, s, sim OPTIONAL MATCH (rec)<-[:ARE_PREREQUISITES]-(prereq_group:PrerequisiteGroup)<-[:INSIDE]-(prereq:Module) WITH prereq, rec, s, sim OPTIONAL MATCH (s:Student)-[t:TAKES]->(prereq:Module) with rec, prereq, s, sim WITH rec, rec.course_name AS course_name, rec.course_code AS course_code, sim.score AS score ORDER BY score DESC RETURN rec")
    List<Module> recommend2(@Param("studentId") String studentId);

//    @Query("CALL db.index.fulltext.queryNodes('moduleIndex', $searchTerm) YIELD node, score WITH COUNT(*) AS total CALL db.index.fulltext.queryNodes('moduleIndex', $searchTerm) YIELD node, score RETURN node.course_code, node.course_name, node.course_information, node.academic_units, score, total SKIP $skip LIMIT $limit")
//    Iterable<Map<String,Object>> search(@Param("searchTerm") String searchTerm, @Param("skip") Integer skip, @Param("limit") Integer limit);
}
