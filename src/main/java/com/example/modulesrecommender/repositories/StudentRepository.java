package com.example.modulesrecommender.repositories;

import com.example.modulesrecommender.models.student.ReadStudentDTO;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.lang.NonNull;

import java.util.Optional;

@RepositoryRestResource(exported = false)
public interface StudentRepository extends Neo4jRepository<ReadStudentDTO, String> {

    @Override
    @NonNull
    Optional<ReadStudentDTO> findById(@NonNull String studentId);

    @Override
    @NonNull
    boolean existsById(@NonNull String studentId);
}
