package com.project.modulesRecommender.models.relationship;

import com.project.modulesRecommender.module.Module;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@RelationshipProperties
public class Similar implements Cloneable{
    @RelationshipId
    private Long id;

    @TargetNode
    private Module module;

    private double score;

    @Override
    public Similar clone() {
        try {
            Similar clone = (Similar) super.clone();

            Module clonedModule = module.clone();
            clone.setModule(clonedModule);

            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
