package com.advertisementproject.zuulgateway.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.handler.ResponseStatusExceptionHandler;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;

@RestControllerAdvice
public class ControllerExceptionHandler extends ResponseStatusExceptionHandler {

    @ExceptionHandler(ResponseException.class)
    @ResponseBody
    protected ResponseEntity<ErrorMessage> handleJwtException(ResponseException exception) {
        return build(
                ErrorMessage.builder()
                        .statusCode(401)
                        .timestamp(Date.from(Instant.now()).toString())
                        .message(exception.getMessage())
                        .build()
        );
    }

    private ResponseEntity<ErrorMessage> build(ErrorMessage errorMessage) {
        return new ResponseEntity<>(errorMessage, HttpStatus.UNAUTHORIZED);
    }
}
