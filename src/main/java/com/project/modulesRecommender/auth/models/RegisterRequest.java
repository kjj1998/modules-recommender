package com.project.modulesRecommender.auth.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Register request class containing the necessary information to register a student
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String firstName;
    private String lastName;
    private String studentId;
    private String password;
    private String major;
    private String email;
    private int yearOfStudy;
}
