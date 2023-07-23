package com.project.modulesRecommender.module.controllers;

import com.project.modulesRecommender.errors.HttpResponse;
import com.project.modulesRecommender.module.models.Module;
import com.project.modulesRecommender.repositories.ModuleRepository;
import com.project.modulesRecommender.module.services.ModuleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/modules")
public class ModuleController {
    private final ModuleRepository moduleRepository;
    private final ModuleService moduleService;

    public ModuleController(ModuleRepository moduleRepository, ModuleService moduleService) {
        this.moduleRepository = moduleRepository;
        this.moduleService = moduleService;
    }

    @GetMapping("/{courseCode}")
    Optional<Module> byCourseCode(@PathVariable String courseCode) {
        return moduleRepository.findById(courseCode);
    }

    /**
     * POST request to retrieve a list of modules based on their course codes
     * @param courseCodes the list of course codes to retrieve
     * @return the response object containing the status code and retrieved module objects
     * @since 1.0
     */
    @PostMapping()
    ResponseEntity<HttpResponse> retrieveModules(@RequestBody List<String> courseCodes) {
        try {
            List<Module> modulesRetrieved = moduleService.retrieveModules(courseCodes);

            return new ResponseEntity<>(
                    new HttpResponse(
                            HttpStatus.OK,
                            "All selected modules retrieved.",
                            modulesRetrieved
                    ),
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new HttpResponse(
                            HttpStatus.INTERNAL_SERVER_ERROR,
                            "Internal Server Error",
                            null
                    ),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
}