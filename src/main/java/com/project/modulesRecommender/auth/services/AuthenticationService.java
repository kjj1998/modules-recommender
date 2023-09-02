package com.project.modulesRecommender.auth.services;

import com.project.modulesRecommender.auth.models.AuthenticationRequest;
import com.project.modulesRecommender.auth.models.AuthenticationResponse;
import com.project.modulesRecommender.auth.models.RegisterRequest;
import com.project.modulesRecommender.auth.models.Role;
import com.project.modulesRecommender.exceptions.CustomErrorException;
import com.project.modulesRecommender.repositories.StudentRepository;
import com.project.modulesRecommender.student.models.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final StudentRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    /**
     * Method to register a user, first check for existing user before saving into the database.
     * Returns a JWT token
     * @param request The RegisterRequest object containing the needed user information
     * @return AuthenticationResponse object containing the JWT token
     */
    public AuthenticationResponse register(RegisterRequest request) {
        if (repository.existsById(request.getStudentId())) {
            throw new CustomErrorException(
                    HttpStatus.BAD_REQUEST,
                    "Student with id " + request.getStudentId() + " already exists!");
        }

        var user = Student.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .studentId(request.getStudentId())
                .build();

        repository.save(user);

        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .build();
    }

    /**
     * Method to authenticate a user, first checks whether said user exists.
     * Returns a JWT token after authenticating user
     * @param request The AuthenticationRequest object containing user info to be authenticated
     * @return JWT token of authenticated user
     */
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );


        String encodedPassword = passwordEncoder.encode(request.getPassword());



        if (!repository.existsById(request.getUsername())) {
            throw new CustomErrorException(
                    HttpStatus.BAD_REQUEST,
                    "Student with id " + request.getUsername() + " does not exist!");
        }

        var user = repository.findById(request.getUsername()).orElseThrow();

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .userId(request.getUsername())
                .build();
    }
}
