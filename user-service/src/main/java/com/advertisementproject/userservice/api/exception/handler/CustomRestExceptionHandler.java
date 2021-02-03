package com.advertisementproject.userservice.api.exception.handler;

import com.advertisementproject.userservice.api.exception.EmailAlreadyRegisteredException;
import com.advertisementproject.userservice.api.exception.IdentificationNumberException;
import com.advertisementproject.userservice.api.exception.EntityNotFoundException;
import com.advertisementproject.userservice.api.exception.response.ApiError;
import lombok.extern.slf4j.Slf4j;
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
import java.util.Collections;
import java.util.List;

/**
 * Exception handler to provide more clear information to the api user when exceptions occur. Exceptions thrown during
 * a controller request will return an ApiError response object in a response entity.
 */
@Slf4j
@RestControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handles showing invalid method argument exceptions to the client
     * @param ex      the exception that was thrown
     * @param headers headers for the request
     * @param status  error status
     * @param request the request that triggered the exception
     * @return Response entity with an ApiError report
     */
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

    /**
     * Handles showing invalid type mismatch exceptions to the client
     *
     * @param ex      the exception that was thrown
     * @param headers headers for the request
     * @param status  error status
     * @param request the request that triggered the exception
     * @return Response entity with an ApiError report
     */
    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.info(ex.getClass().getName());
        //
        final String error = ex.getValue() + " value for " + ex.getPropertyName() + " should be of type " + ex.getRequiredType();
        List<String> errors = Collections.singletonList(error);

        return getAndLogApiError(ex.getMessage(), HttpStatus.BAD_REQUEST, errors);
    }

    /**
     * Handles showing constraint violation exceptions to the client
     *
     * @param ex the exception that was thrown
     * @return Response entity with an ApiError report
     */
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

    /**
     * Handles showing email already registered exceptions to the client
     *
     * @param ex the exception that was thrown
     * @return Response entity with an ApiError report
     */
    @ExceptionHandler({EmailAlreadyRegisteredException.class})
    public ResponseEntity<Object> handleConflictError(Exception ex) {
        return getAndLogApiError(ex, HttpStatus.CONFLICT);
    }

    /**
     * Handles showing user not found exceptions to the client
     *
     * @param ex the exception that was thrown
     * @return Response entity with an ApiError report
     */
    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(Exception ex) {
        return getAndLogApiError(ex, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles showing bad request exceptions related to personal id number or organization number to the client
     *
     * @param ex the exception that was thrown
     * @return Response entity with an ApiError report
     */
    @ExceptionHandler({IdentificationNumberException.class})
    public ResponseEntity<Object> handleCustomBadRequestException(Exception ex) {
        return getAndLogApiError(ex, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles showing internal server exceptions to the client
     *
     * @param ex the exception that was thrown
     * @return Response entity with an ApiError report
     */
    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleInternalServerError(Exception ex) {
        return getAndLogApiError(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Helper method for creating a response entity with a ApiError report
     *
     * @param ex         the exception that was thrown
     * @param httpStatus the error status
     * @return Response entity with an ApiError report
     */
    private ResponseEntity<Object> getAndLogApiError(Exception ex, HttpStatus httpStatus) {
        ApiError apiError = ApiError.builder()
                .status(httpStatus)
                .message(ex.getMessage())
                .timestamp(Instant.now())
                .build();

        log.warn(apiError.toString());
        return new ResponseEntity<>(apiError, httpStatus);
    }

    /**
     * Helper method for creating a response entity with a ApiError report pertaining to potentially multiple errors
     *
     * @param errorMessage a general error message for the errors
     * @param httpStatus   the error status
     * @param errors       a list of errors that occurred
     * @return Response entity with an ApiError report
     */
    private ResponseEntity<Object> getAndLogApiError(String errorMessage, HttpStatus httpStatus, List<String> errors) {
        ApiError apiError = ApiError.builder()
                .status(httpStatus)
                .message(errorMessage)
                .timestamp(Instant.now())
                .errors(errors)
                .build();

        log.warn(apiError.toString());
        return new ResponseEntity<>(apiError, httpStatus);
    }
}
