package com.project.modulesRecommender.repositories;

import com.project.modulesRecommender.module.models.Module;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.lang.NonNull;

import java.util.Optional;

@RepositoryRestResource(exported = false)
public interface ModuleRepository extends Neo4jRepository<Module, String> {
    @NonNull
    Optional<Module> findById(@NonNull String courseCode);
}
