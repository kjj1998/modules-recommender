package com.project.modulesRecommender.module.services;

import com.project.modulesRecommender.exceptions.CustomErrorException;
import com.project.modulesRecommender.module.models.Module;
import com.project.modulesRecommender.repositories.ModuleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ModuleService {
    private final ModuleRepository moduleRepository;

    public ModuleService(ModuleRepository moduleRepository) {
        this.moduleRepository = moduleRepository;
    }

    public List<Module> retrieveModules(List<String> courseCodes) {
        List<Module> modulesRetrieved = new ArrayList<>();

        for (String courseCode : courseCodes) {
            Optional<Module> module = moduleRepository.findById(courseCode);
            module.ifPresent(modulesRetrieved::add);
        }

        if (modulesRetrieved.size() != courseCodes.size()) {
            throw new CustomErrorException(
                    HttpStatus.BAD_REQUEST,
                    "Some of the course codes entered are invalid!");
        }

        return modulesRetrieved;
    }
}
