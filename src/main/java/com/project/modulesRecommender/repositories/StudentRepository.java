package com.project.modulesRecommender.repositories;

import com.project.modulesRecommender.student.models.Student;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.lang.NonNull;

import java.util.Optional;

@RepositoryRestResource(exported = false)
public interface StudentRepository extends Neo4jRepository<Student, String> {

    @Override
    @NonNull
    Optional<Student> findById(@NonNull String studentId);

    @Override
    @NonNull
    boolean existsById(@NonNull String studentId);
}
