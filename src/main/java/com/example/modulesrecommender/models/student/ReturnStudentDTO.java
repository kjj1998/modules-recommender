package com.example.modulesrecommender.models.student;

import java.util.List;

public class ReturnStudentDTO {
    public String studentId;
    public String major;
    public String name;
    public Integer yearOfStudy;
    public List<String> modules;

    public ReturnStudentDTO(String studentId, String major, String name, Integer yearOfStudy, List<String> modules) {
        this.studentId = studentId;
        this.major = major;
        this.name = name;
        this.yearOfStudy = yearOfStudy;
        this.modules = modules;
    }
}
