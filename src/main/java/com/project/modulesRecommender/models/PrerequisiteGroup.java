package com.project.modulesRecommender.models;

import com.project.modulesRecommender.module.models.Module;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.ArrayList;
import java.util.List;

@Node
public class PrerequisiteGroup {

    @Id @Property("group_id")
    private String groupId;

    @JsonProperty("modules")
    @JsonIgnoreProperties({
            "topics", "prerequisites", "academicUnits", "broadeningAndDeepeningElective", "community",
            "courseInformation", "courseName", "faculty","gradeType"})
    @Relationship(type = "INSIDE", direction = Relationship.Direction.INCOMING)
    private List<Module> modules = new ArrayList<>();

    public PrerequisiteGroup(String groupId, List<Module> modules) {
        this.groupId = groupId;
        this.modules = modules;
    }

    public String getGroupId() {
        return groupId;
    }

    public List<Module> getModules() {
        return modules;
    }
}
