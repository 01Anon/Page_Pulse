package com.pagepulse.exception;

import com.pagepulse.dto.AuditResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.MalformedURLException;
import java.net.SocketTimeoutException;

/**
 * Centralized exception handler for the entire application.
 * Catches specific exceptions and maps them to clean JSON error responses,
 * ensuring the API never returns raw stack traces to the client.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles invalid URL format (e.g., "not-a-url", "ftp://something").
     */
    @ExceptionHandler(MalformedURLException.class)
    public ResponseEntity<AuditResponse> handleMalformedUrl(MalformedURLException ex) {
        AuditResponse error = new AuditResponse()
                .setError(true)
                .setMessage("Invalid URL format. Please provide a valid HTTP or HTTPS URL.");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    /**
     * Handles connection and read timeouts.
     */
    @ExceptionHandler(SocketTimeoutException.class)
    public ResponseEntity<AuditResponse> handleTimeout(SocketTimeoutException ex) {
        AuditResponse error = new AuditResponse()
                .setError(true)
                .setMessage("Connection timed out. The target server took too long to respond.");

        return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body(error);
    }

    /**
     * Handles Bean Validation failures (e.g., blank URL submitted).
     * Triggered when @Valid on the controller method fails.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<AuditResponse> handleValidation(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
                .orElse("Validation failed");

        AuditResponse error = new AuditResponse()
                .setError(true)
                .setMessage(message);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    /**
     * Catch-all handler for any unexpected exception.
     * Prevents raw stack traces from leaking to the client.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<AuditResponse> handleGeneral(Exception ex) {
        AuditResponse error = new AuditResponse()
                .setError(true)
                .setMessage("An unexpected error occurred while processing the request.");

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
