package com.example.modulesrecommender.models;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Property;

public class Student {

    @Id @Property("student_id")
    private String studentId;
    @Property("major")
    private String major;

    public Student(String studentId, String major) {
        this.studentId = studentId;
        this.major = major;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getMajor() {
        return major;
    }
}
