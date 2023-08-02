package com.project.modulesRecommender.module;

import com.project.modulesRecommender.models.PrerequisiteGroup;
import com.project.modulesRecommender.models.Topic;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Module implements Cloneable{
    @Id
    @Property("course_code")
    public String courseCode;

    @Property("academic_units")
    public Integer academicUnits;

    @Property("bde")
    public Boolean broadeningAndDeepeningElective;

    @JsonIgnore
    @Property("community")
    public Integer community;

    @Property("course_information")
    public String courseInformation;

    @Property("course_name")
    public String courseName;

    @Property("faculty")
    public String faculty;

    @Property("grade_type")
    public String gradeType;

    @JsonProperty
    @Relationship(type = "CONTAIN", direction = Relationship.Direction.OUTGOING)
    public List<Topic> topics;

    @JsonProperty(value = "prerequisites")
    @Relationship(type = "ARE_PREREQUISITES", direction = Relationship.Direction.INCOMING)
    public List<PrerequisiteGroup> prerequisites;

    @Override
    public Module clone() {
        try {
            Module clone = (Module) super.clone();
            List<Topic> clonedTopics;
            List<PrerequisiteGroup> clonedPrereqs;

            if (topics != null && !topics.isEmpty()) {
                clonedTopics = topics.stream()
                        .map(Topic::clone)
                        .toList();
            } else {
                clonedTopics = new ArrayList<>();
            }
            clone.setTopics(clonedTopics);

            if (prerequisites != null && !prerequisites.isEmpty()) {
                clonedPrereqs = prerequisites.stream()
                        .map(PrerequisiteGroup::clone)
                        .toList();
            } else {
                clonedPrereqs = new ArrayList<>();
            }
            clone.setPrerequisites(clonedPrereqs);

            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}