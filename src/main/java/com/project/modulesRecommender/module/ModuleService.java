package com.project.modulesRecommender.module;

import com.project.modulesRecommender.exceptions.CustomErrorException;
import com.project.modulesRecommender.module.models.moduleSearchInterface;
import com.project.modulesRecommender.module.models.moduleSearchInterface.SearchResult;
import com.project.modulesRecommender.module.models.Module;
import com.project.modulesRecommender.repositories.ModuleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ModuleService {
    private final ModuleRepository moduleRepository;
    private final moduleSearchInterface moduleSearchInterface;

    public ModuleService(ModuleRepository moduleRepository, moduleSearchInterface moduleSearchInterface) {
        this.moduleRepository = moduleRepository;
        this.moduleSearchInterface = moduleSearchInterface;
    }

    public Module retrieveModule(String courseCode) {
        Optional<Module> retrievedModule = moduleRepository.findById(courseCode);

        if (retrievedModule.isEmpty()) {
            throw new CustomErrorException(
                    HttpStatus.BAD_REQUEST,
                    "Course code " + courseCode + " is invalid!");
        }

        return retrievedModule.get();
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

    public Collection<SearchResult> searchModules(String searchTerm, Integer skip, Integer limit) {

        Collection<SearchResult> results = moduleSearchInterface.searchForModules(searchTerm, skip == null ? 0 : skip, limit);

        return results;
    }
}
