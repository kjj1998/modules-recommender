package com.project.modulesRecommender.auth.controllers;

import com.project.modulesRecommender.auth.models.AuthenticationRequest;
import com.project.modulesRecommender.auth.models.AuthenticationResponse;
import com.project.modulesRecommender.auth.models.RegisterRequest;
import com.project.modulesRecommender.auth.services.AuthenticationService;
import com.project.modulesRecommender.errors.HttpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<HttpResponse> register(@RequestBody RegisterRequest request) {
        try {
            AuthenticationResponse response = service.register(request);

            return new ResponseEntity<>(
                    new HttpResponse(
                            HttpStatus.OK,
                            "Student with id " + request.getStudentId() + " registered.",
                            response
                    ),
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new HttpResponse(
                            HttpStatus.INTERNAL_SERVER_ERROR,
                            "Internal Server Error",
                            null
                    ),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<HttpResponse> authenticate(@RequestBody AuthenticationRequest request) {
        try {
            var response = service.authenticate(request);

            return new ResponseEntity<>(
                    new HttpResponse(
                            HttpStatus.OK,
                            "Student with id " + request.getStudentId() + " authenticated.",
                            response
                    ),
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new HttpResponse(
                            HttpStatus.INTERNAL_SERVER_ERROR,
                            "Internal Server Error",
                            null
                    ),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
}
