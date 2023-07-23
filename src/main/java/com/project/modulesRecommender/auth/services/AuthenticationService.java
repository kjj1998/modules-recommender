package com.project.modulesRecommender.auth.services;

import com.project.modulesRecommender.auth.models.AuthenticationRequest;
import com.project.modulesRecommender.auth.models.AuthenticationResponse;
import com.project.modulesRecommender.auth.models.RegisterRequest;
import com.project.modulesRecommender.auth.models.Role;
import com.project.modulesRecommender.exceptions.CustomErrorException;
import com.project.modulesRecommender.repositories.NewStudentRepo;
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

    public AuthenticationResponse register(RegisterRequest request) {
        if (repository.existsById(request.getStudentId())) {
            throw new CustomErrorException(
                    HttpStatus.BAD_REQUEST,
                    "Student with id " + request.getStudentId() + " already exists!");
        }

        var user = Student.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .major(request.getMajor())
                .yearOfStudy(request.getYearOfStudy())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .studentId(request.getStudentId())
                .build();

        repository.save(user);

        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getStudentId(),
                        request.getPassword()
                )
        );

        if (!repository.existsById(request.getStudentId())) {
            throw new CustomErrorException(
                    HttpStatus.BAD_REQUEST,
                    "Student with id " + request.getStudentId() + " does not exist!");
        }

        var user = repository.findById(request.getStudentId()).orElseThrow();

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
