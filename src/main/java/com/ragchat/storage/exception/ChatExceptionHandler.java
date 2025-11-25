package com.ragchat.storage.exception;

import com.ragchat.storage.util.AppConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler for the application.
 * Captures specific exceptions and returns standardized JSON error responses.
 */
@ControllerAdvice
public class ChatExceptionHandler {

    /**
     * Handles ResourceNotFoundException.
     *
     * @param ex The exception instance.
     * @return A ResponseEntity containing the error details and HTTP 404 status.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleResourceNotFound(ResourceNotFoundException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put(AppConstants.KEY_TIMESTAMP, LocalDateTime.now());
        body.put(AppConstants.KEY_MESSAGE, ex.getMessage());
        body.put(AppConstants.KEY_STATUS, HttpStatus.NOT_FOUND.value());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles validation errors (e.g., @Valid failures).
     *
     * @param ex The exception instance.
     * @return A ResponseEntity containing field-specific error messages and HTTP
     *         400 status.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put(AppConstants.KEY_TIMESTAMP, LocalDateTime.now());
        body.put(AppConstants.KEY_STATUS, HttpStatus.BAD_REQUEST.value());

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        body.put(AppConstants.KEY_ERRORS, errors);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles generic exceptions.
     *
     * @param ex The exception instance.
     * @return A ResponseEntity containing a generic error message and HTTP 500
     *         status.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGlobalException(Exception ex) {
        Map<String, Object> body = new HashMap<>();
        body.put(AppConstants.KEY_TIMESTAMP, LocalDateTime.now());
        body.put(AppConstants.KEY_MESSAGE, AppConstants.ERR_UNEXPECTED);
        body.put(AppConstants.KEY_DETAILS, ex.getMessage());
        body.put(AppConstants.KEY_STATUS, HttpStatus.INTERNAL_SERVER_ERROR.value());

        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
