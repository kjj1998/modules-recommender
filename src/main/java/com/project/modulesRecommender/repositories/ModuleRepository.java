package com.project.modulesRecommender.repositories;

import com.project.modulesRecommender.module.models.Module;
import org.springframework.data.repository.Repository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource(exported = false)
public interface ModuleRepository extends Repository<Module, String> {
    Optional<Module> findById(String courseCode);

}
