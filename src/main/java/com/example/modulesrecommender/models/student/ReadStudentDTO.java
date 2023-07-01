package com.example.modulesrecommender.models.student;

import com.example.modulesrecommender.models.module.Module;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;

@Node("Student")
public class ReadStudentDTO {

    @Id @Property("student_id")
    private String studentId;
    @Property("major")
    private String major;
    @Property("name")
    private String name;
    @Property("year_of_study")
    private Integer yearOfStudy;
    @JsonProperty("modules")
    @Relationship(type = "TAKES", direction = Relationship.Direction.OUTGOING)
    private List<Module> modules;

    public ReadStudentDTO(String studentId, String major, String name, Integer yearOfStudy, List<Module> modules) {
        this.studentId = studentId;
        this.major = major;
        this.name = name;
        this.yearOfStudy = yearOfStudy;
        this.modules = modules;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getMajor() {
        return major;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getYearOfStudy() {
        return yearOfStudy;
    }

    public void setYearOfStudy(Integer yearOfStudy) {
        this.yearOfStudy = yearOfStudy;
    }

    public List<Module> getModules() {
        return modules;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }
}
