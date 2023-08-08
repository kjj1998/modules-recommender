package com.project.modulesRecommender.recommendation;

import com.project.modulesRecommender.errors.HttpResponse;
import com.project.modulesRecommender.module.models.ModuleRead;
import com.project.modulesRecommender.recommendation.models.moduleRecInterface;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/api/v1/recommendation")
public class RecommendationController {
    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    /**
     * GET request to retrieve module recommendations for a single student Id
     * @param studentId the id to retrieve recommendations for
     * @return the response object containing the status code and list of recommended module objects
     * @since 1.0
     */
    @GetMapping("/{studentId}")
    ResponseEntity<HttpResponse> getRecommendations(@PathVariable String studentId) {
        Collection<ModuleRead> recommendedModules = recommendationService.recommendModules(studentId);

        return new ResponseEntity<>(
                new HttpResponse(
                        HttpStatus.OK,
                        recommendedModules.size() + " recommended modules",
                        recommendedModules
                ),
                HttpStatus.OK
        );
    }
}