package com.project.modulesRecommender.recommendation;

import com.project.modulesRecommender.auth.services.JwtService;
import com.project.modulesRecommender.exceptions.CustomErrorException;
import com.project.modulesRecommender.module.models.ModuleRead;
import com.project.modulesRecommender.recommendation.models.RecommendationsDTO;
import com.project.modulesRecommender.recommendation.models.moduleRecInterface;
import com.project.modulesRecommender.repositories.ModuleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class RecommendationService {
    private final ModuleRepository moduleRepository;
    private final JwtService jwtService;
    private final moduleRecInterface moduleRecInterface;

    public RecommendationService(ModuleRepository moduleRepository, JwtService jwtService, moduleRecInterface moduleRecInterface) {
        this.moduleRepository = moduleRepository;
        this.jwtService = jwtService;
        this.moduleRecInterface = moduleRecInterface;
    }

    public RecommendationsDTO recommendModules(String studentId) {

        // Get the Authentication object from the SecurityContextHolder
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Extract the JWT token from the Authentication object
        String token = (String) authentication.getCredentials();
        String userId = jwtService.extractUsername(token);

        if (!userId.equals(studentId)) {
            throw new CustomErrorException(
                    HttpStatus.BAD_REQUEST,
                    "User with id " + userId + " not authorized to get recommendations for student with id " + studentId);
        }

          return moduleRecInterface.recommendModules(studentId);
//        return moduleRepository.recommend(studentId);
    }
}
