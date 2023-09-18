package com.project.modulesRecommender.recommendation.models;

import com.project.modulesRecommender.module.models.ModuleRead;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecommendationsDTO {
    public Collection<ModuleRead>  cfRecommendations;
    public Collection<ModuleRead> cbfRecommendations;
}
