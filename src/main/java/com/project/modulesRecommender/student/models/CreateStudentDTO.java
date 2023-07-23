package com.project.modulesRecommender.student.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Property;

import java.util.List;

public class CreateStudentDTO {
    @Id
    @Property("student_id")
    private String studentId;
    @Property("major")
    private String major;

    @Property("name")
    private String name;

    @Property("year_of_study")
    private Integer yearOfStudy;

    @JsonProperty("modules")
    private List<String> courseCode;

    public CreateStudentDTO(String studentId, String major, String name, Integer yearOfStudy, List<String> courseCode) {
        this.studentId = studentId;
        this.major = major;
        this.name = name;
        this.yearOfStudy = yearOfStudy;
        this.courseCode = courseCode;
    }

    public String getStudentId() {
        return studentId;
    }
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
    public String getMajor() {
        return major;
    }
    public void setMajor(String major) { this.major = major; }
    public String getName() { return name; }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getYearOfStudy() {
        return yearOfStudy;
    }
    public void setYearOfStudy(Integer yearOfStudy) {
        this.yearOfStudy = yearOfStudy;
    }
    public List<String> getCourseCode() { return courseCode; }
    public void setCourseCode(List<String> courseCode) { this.courseCode = courseCode; }
}
