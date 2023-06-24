package com.example.modulesrecommender.models;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

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