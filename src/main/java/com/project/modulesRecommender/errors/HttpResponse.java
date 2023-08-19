package com.project.modulesRecommender.errors;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;

import java.util.Date;

public class HttpResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date timestamp;
    @JsonProperty("http_code")
    private int code;
    @JsonProperty("status")
    private String status;
    @JsonProperty("message")
    private String message;
    @JsonProperty("data")
    private Object data;

    @JsonProperty("total")
    private int total;

    public HttpResponse() {
        this.timestamp = new Date();
    }

    public HttpResponse(HttpStatus httpStatus, String message) {
        this();

        this.code = httpStatus.value();
        this.status = httpStatus.name();
        this.message = message;
    }

    public HttpResponse(HttpStatus httpStatus, String message, Object data) {
        this(httpStatus, message);

        this.data = data;
    }

    public HttpResponse(HttpStatus httpStatus, String message, Object data, int total) {
        this(httpStatus, message, data);

        this.total = total;
    }
}
