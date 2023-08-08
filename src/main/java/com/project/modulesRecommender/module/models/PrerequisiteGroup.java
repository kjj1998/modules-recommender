package com.project.modulesRecommender.module.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Node
public class PrerequisiteGroup implements Cloneable {

    @Id @Property("group_id")
    private String groupId;

    @JsonProperty("modules")
    @JsonIgnoreProperties({
            "topics", "prerequisites", "academicUnits", "broadeningAndDeepeningElective", "community",
            "courseInformation", "courseName", "faculty","gradeType"})
    @Relationship(type = "INSIDE", direction = Relationship.Direction.INCOMING)
    private List<Module> modules;

    @Override
    public PrerequisiteGroup clone() {
        try {
            PrerequisiteGroup clone = (PrerequisiteGroup) super.clone();

            List<Module> clonedModules;
            if (modules != null && !modules.isEmpty()) {
                clonedModules = modules.stream()
                        .map(Module::clone)
                        .toList();
            } else {
                clonedModules = new ArrayList<>();
            }
            clone.setModules(clonedModules);

            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
