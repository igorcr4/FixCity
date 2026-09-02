package com.fixcity.fixcity.exception;

import com.fixcity.fixcity.csc.exception.CscClientException;
import com.fixcity.fixcity.csc.exception.CscConfigurationException;
import com.fixcity.fixcity.geography.LocationNotResolvedException;
import com.fixcity.fixcity.user.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsernameTakenException.class)
    public ResponseEntity<ErrorResponse> handleUsernameTaken(UsernameTakenException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(
                        ex.getMessage(),
                        Instant.now(),
                        "USERNAME_TAKEN"
                ));
    }

    @ExceptionHandler(EmailNotFound.class)
    public ResponseEntity<ErrorResponse> handleEmailNotFound(EmailNotFound ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(
                        ex.getMessage(),
                        Instant.now(),
                        "EMAIL_NOT_FOUND"
                ));
    }

    @ExceptionHandler(EmailTakenException.class)
    public ResponseEntity<ErrorResponse> handleEmailTaken(EmailTakenException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(
                        ex.getMessage(),
                        Instant.now(),
                        "EMAIL_TAKEN"
                ));
    }

    @ExceptionHandler(IncorrectPasswordException.class)
    public ResponseEntity<ErrorResponse> handleIncorrectPassword(IncorrectPasswordException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponse(
                        ex.getMessage(),
                        Instant.now(),
                        "INCORRECT_PASSWORD"
                ));
    }

    @ExceptionHandler(UsernameNotFound.class)
    public ResponseEntity<ErrorResponse> handleUsernameNotFound(UsernameNotFound ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(
                        ex.getMessage(),
                        Instant.now(),
                        "USERNAME_NOT_FOUND"
                ));
    }

    @ExceptionHandler(WeakPasswordException.class)
    public ResponseEntity<ErrorResponse> handleWeakPassword(WeakPasswordException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(
                        ex.getMessage(),
                        Instant.now(),
                        "WEAK_PASSWORD"
                ));
    }

    @ExceptionHandler(CscConfigurationException.class)
    public ResponseEntity<ErrorResponse> handleCscConfiguration(CscConfigurationException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(
                        ex.getMessage(),
                        Instant.now(),
                        "CSC_CONFIGURATION_ERROR"
                ));
    }

    @ExceptionHandler(CscClientException.class)
    public ResponseEntity<ErrorResponse> handleCscClient(CscClientException ex) {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                .body(new ErrorResponse(
                        ex.getMessage(),
                        Instant.now(),
                        "CSC_CLIENT_ERROR"
                ));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleUnreadableRequest(HttpMessageNotReadableException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(
                        "Datele trimise nu sunt valide.",
                        Instant.now(),
                        "INVALID_REQUEST_BODY"
                ));
    }

    @ExceptionHandler(MissingServletRequestPartException.class)
    public ResponseEntity<ErrorResponse> handleMissingRequestPart(MissingServletRequestPartException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(
                        "Lipsește o parte obligatorie din request.",
                        Instant.now(),
                        "MISSING_REQUEST_PART"
                ));
    }

    @ExceptionHandler(LocationNotResolvedException.class)
    public ResponseEntity<ErrorResponse> handleLocationNotResolved(LocationNotResolvedException ex) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(  new ErrorResponse(
                        ex.getMessage(),
                        Instant.now(),
                        "LOCATION_NOT_RESOLVED"
                ));
    }
}
