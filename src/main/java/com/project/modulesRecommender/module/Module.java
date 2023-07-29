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

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Node
public class Module {
    @Id
    @Property("course_code")
    private String courseCode;

    @Property("academic_units")
    private Integer academicUnits;

    @Property("bde")
    private Boolean broadeningAndDeepeningElective;

    @JsonIgnore
    @Property("community")
    private Integer community;

    @Property("course_information")
    private String courseInformation;

    @Property("course_name")
    private String courseName;

    @Property("faculty")
    private String faculty;

    @Property("grade_type")
    private String gradeType;

    @JsonProperty
    @Relationship(type = "CONTAIN", direction = Relationship.Direction.OUTGOING)
    private List<Topic> topics;

    @JsonProperty(value = "prerequisites")
    @Relationship(type = "ARE_PREREQUISITES", direction = Relationship.Direction.INCOMING)
    private List<PrerequisiteGroup> prerequisites;
}