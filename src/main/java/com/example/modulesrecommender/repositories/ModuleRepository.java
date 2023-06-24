package com.example.modulesrecommender.repositories;

import com.example.modulesrecommender.models.Module;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface ModuleRepository extends Repository<Module, String> {
    Optional<Module> findById(String courseCode);

}
