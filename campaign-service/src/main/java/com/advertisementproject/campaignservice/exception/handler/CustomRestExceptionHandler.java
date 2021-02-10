package com.advertisementproject.campaignservice.exception.handler;

import com.advertisementproject.campaignservice.exception.EntityNotFoundException;
import com.advertisementproject.campaignservice.exception.UnauthorizedAccessException;
import com.advertisementproject.campaignservice.exception.response.ApiError;
import lombok.extern.slf4j.Slf4j;
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

/**
 * Exception handler to provide more clear information to the api user when exceptions occur. Exceptions thrown during
 * a controller request will return an ApiError response object in a response entity.
 */
@Slf4j
@RestControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handles showing invalid method argument exceptions to the client
     *
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
        logger.info(ex.getClass().getName());
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
     * Handles showing not found exceptions to the client
     *
     * @param ex the exception that was thrown
     * @return Response entity with an ApiError report
     */
    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(Exception ex) {
        return getAndLogApiError(ex, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles showing forbidden access exceptions to the client
     *
     * @param ex the exception that was thrown
     * @return Response entity with an ApiError report
     */
    @ExceptionHandler({UnauthorizedAccessException.class})
    public ResponseEntity<Object> handleForbiddenAccessException(Exception ex) {
        return getAndLogApiError(ex, HttpStatus.FORBIDDEN);
    }

    /**
     * Handles showing illegal argument exceptions to the client
     *
     * @param ex the exception that was thrown
     * @return Response entity with an ApiError report
     */
    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<Object> handleBadRequest(Exception ex) {
        return getAndLogApiError(ex, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles showing internal server error exceptions to the client
     *
     * @param ex the exception that was thrown
     * @return Response entity with an ApiError report
     */
    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleInternalServerError(Exception ex) {
        List<String> errors = Collections.singletonList("An internal error occured: " + ex.getClass().getSimpleName());
        log.warn(Arrays.toString(ex.getStackTrace()));
        return getAndLogApiError(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, errors);
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
                .timestamp(Instant.now().toString())
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
                .timestamp(Instant.now().toString())
                .errors(errors)
                .build();

        log.warn(apiError.toString());
        return new ResponseEntity<>(apiError, httpStatus);
    }
}
