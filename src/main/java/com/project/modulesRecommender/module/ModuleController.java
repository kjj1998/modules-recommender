package com.project.modulesRecommender.module;

import com.project.modulesRecommender.errors.HttpResponse;
import com.project.modulesRecommender.repositories.ModuleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/v1/modules")
public class ModuleController {
    private final ModuleRepository moduleRepository;
    private final ModuleService moduleService;

    public ModuleController(ModuleRepository moduleRepository, ModuleService moduleService) {
        this.moduleRepository = moduleRepository;
        this.moduleService = moduleService;
    }

    /**
     * GET request to retrieve a single module based on its course code
     * @param courseCode the course code to retrieve
     * @return the response object containing the status code and retrieved module object
     * @since 1.0
     */
    @GetMapping("/{courseCode}")
    ResponseEntity<HttpResponse> byCourseCode(@PathVariable String courseCode) {
        Module module = moduleService.retrieveModule(courseCode);

        return new ResponseEntity<>(
                new HttpResponse(
                        HttpStatus.OK,
                        "Module with course code " + courseCode + " is retrieved.",
                        module
                ),
                HttpStatus.OK
        );
    }

    /**
     * POST request to retrieve a list of modules based on their course codes
     * @param courseCodes the list of course codes to retrieve
     * @return the response object containing the status code and retrieved module objects
     * @since 1.0
     */
    @PostMapping()
    ResponseEntity<HttpResponse> retrieveModules(@RequestBody List<String> courseCodes) {
        List<Module> modulesRetrieved = moduleService.retrieveModules(courseCodes);

        return new ResponseEntity<>(
                new HttpResponse(
                        HttpStatus.OK,
                        "All selected modules retrieved.",
                        modulesRetrieved
                ),
                HttpStatus.OK
        );
    }

    @GetMapping("/search/{searchTerm}/{skip}/{limit}")
    ResponseEntity<HttpResponse> searchForModules(@PathVariable String searchTerm, @PathVariable Integer skip, @PathVariable Integer limit) {
        Collection<NonDomainResult.SearchResult> modulesRetrieved = moduleService.searchModules(searchTerm, skip, limit);

        return new ResponseEntity<>(
                new HttpResponse(
                        HttpStatus.OK,
                        "Returning search results",
                        modulesRetrieved
                ),
                HttpStatus.OK
        );
    }
}