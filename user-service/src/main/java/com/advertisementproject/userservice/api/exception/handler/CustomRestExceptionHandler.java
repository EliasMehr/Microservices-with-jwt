package com.advertisementproject.userservice.api.exception.handler;

import com.advertisementproject.userservice.api.exception.EmailAlreadyRegisteredException;
import com.advertisementproject.userservice.api.exception.IdentificationNumberException;
import com.advertisementproject.userservice.api.exception.PermissionsNotFoundException;
import com.advertisementproject.userservice.api.exception.UserNotFoundException;
import com.advertisementproject.userservice.api.response.ApiError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    @NonNull
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errors = new ArrayList<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.add(fieldError.getField() + ": " + fieldError.getDefaultMessage());
        }
        for (ObjectError globalError : ex.getBindingResult().getGlobalErrors()) {
            errors.add(globalError.getObjectName() + ": " + globalError.getDefaultMessage());
        }
        return getAndLogApiError(ex.getMessage(), HttpStatus.BAD_REQUEST, errors);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.info(ex.getClass().getName());
        //
        final String error = ex.getValue() + " value for " + ex.getPropertyName() + " should be of type " + ex.getRequiredType();
        List<String> errors = Collections.singletonList(error);

        return getAndLogApiError(ex.getMessage(), HttpStatus.BAD_REQUEST, errors);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolation(
            ConstraintViolationException ex) {
        List<String> errors = new ArrayList<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            String violationPath = violation.getPropertyPath().toString();
            errors.add(violationPath.substring(violationPath.lastIndexOf('.') + 1) + ": " + violation.getMessage());
        }
        return getAndLogApiError("Field error(s) for request", HttpStatus.BAD_REQUEST, errors);
    }

    @ExceptionHandler({EmailAlreadyRegisteredException.class})
    public ResponseEntity<Object> handleConflictError(Exception ex) {
        return getAndLogApiError(ex, HttpStatus.CONFLICT);
    }

    @ExceptionHandler({IdentificationNumberException.class})
    public ResponseEntity<Object> handleCustomBadRequestException(Exception ex) {
        return getAndLogApiError(ex, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({UserNotFoundException.class, PermissionsNotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(Exception ex) {
        return getAndLogApiError(ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleInternalServerError(Exception ex) {
        return getAndLogApiError(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    private ResponseEntity<Object> getAndLogApiError(Exception ex, HttpStatus httpStatus) {
        ApiError apiError = ApiError.builder()
                .status(httpStatus)
                .message(ex.getMessage())
                .timestamp(Instant.now())
                .build();

        logger.warn(apiError.toString());
        return new ResponseEntity<>(apiError, httpStatus);
    }

    private ResponseEntity<Object> getAndLogApiError(String errorMessage, HttpStatus httpStatus, List<String> errors) {
        ApiError apiError = ApiError.builder()
                .status(httpStatus)
                .message(errorMessage)
                .timestamp(Instant.now())
                .errors(errors)
                .build();

        logger.warn(apiError.toString());
        return new ResponseEntity<>(apiError, httpStatus);
    }
}
