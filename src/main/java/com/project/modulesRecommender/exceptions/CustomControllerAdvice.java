package com.project.modulesRecommender.exceptions;

import com.project.modulesRecommender.errors.HttpResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomControllerAdvice {

    @ExceptionHandler(CustomErrorException.class)
    public ResponseEntity<HttpResponse> handleCustomErrorExceptions(Exception e) {

        CustomErrorException customErrorException = (CustomErrorException) e;
        HttpStatus status = customErrorException.getStatus();

        return new ResponseEntity<>(new HttpResponse(status, customErrorException.getMessage()), status);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<HttpResponse> handleExceptions() {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        return new ResponseEntity<>(new HttpResponse(status, "INTERNAL SERVER ERROR"), status);
    }
}

