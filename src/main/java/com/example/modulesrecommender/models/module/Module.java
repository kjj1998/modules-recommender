package com.example.modulesrecommender.models.module;

import com.example.modulesrecommender.models.PrerequisiteGroup;
import com.example.modulesrecommender.models.Topic;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.ArrayList;
import java.util.List;

@Node
public class Module {
    @Id
    @Property("course_code")
    private String courseCode;
    @Property("academic_units")
    private Integer academicUnits;
    @Property("bde")
    private Boolean broadeningAndDeepeningElective;
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

    public String getCourseCode() {
        return courseCode;
    }

    public Integer getAcademicUnits() {
        return academicUnits;
    }

    public Boolean getBroadeningAndDeepeningElective() {
        return broadeningAndDeepeningElective;
    }

    public Integer getCommunity() {
        return community;
    }

    public String getCourseInformation() {
        return courseInformation;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getFaculty() {
        return faculty;
    }

    public String getGradeType() {
        return gradeType;
    }
}