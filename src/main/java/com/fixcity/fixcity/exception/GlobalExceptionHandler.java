package com.fixcity.fixcity.exception;

import com.fixcity.fixcity.comment.exception.CommentNotFoundException;
import com.fixcity.fixcity.comment.exception.NotCommentOwnerException;
import com.fixcity.fixcity.confirmation.exception.CannotConfirmOwnReportException;
import com.fixcity.fixcity.confirmation.exception.ReportAlreadyResolvedException;
import com.fixcity.fixcity.csc.exception.CscClientException;
import com.fixcity.fixcity.csc.exception.CscConfigurationException;
import com.fixcity.fixcity.geography.LocationNotResolvedException;
import com.fixcity.fixcity.municipality.exception.MunicipalityNotFoundException;
import com.fixcity.fixcity.municipality.exception.MunicipalityResolutionException;
import com.fixcity.fixcity.municipalityrequest.exception.DuplicateRequestException;
import com.fixcity.fixcity.municipalityrequest.exception.RequestNotFoundException;
import com.fixcity.fixcity.municipalityrequest.exception.RequestNotPendingException;
import com.fixcity.fixcity.report.exception.ImageUploadFailedException;
import com.fixcity.fixcity.report.exception.ModifyReportException;
import com.fixcity.fixcity.report.exception.MunicipalityNotAssignedException;
import com.fixcity.fixcity.report.exception.ReportNotFoundException;
import com.fixcity.fixcity.report.exception.UploadImageException;
import com.fixcity.fixcity.subscription.exception.StripeOperationException;
import com.fixcity.fixcity.subscription.exception.SubscriptionNotFoundException;
import com.fixcity.fixcity.subscription.exception.WebhookProcessingException;
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
                .body(new ErrorResponse(
                        ex.getMessage(),
                        Instant.now(),
                        "LOCATION_NOT_RESOLVED"
                ));
    }

    @ExceptionHandler(UploadImageException.class)
    public ResponseEntity<ErrorResponse> handleUploadImage(UploadImageException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(
                        ex.getMessage(),
                        Instant.now(),
                        "INVALID_IMAGE"
                ));
    }

    @ExceptionHandler(ImageUploadFailedException.class)
    public ResponseEntity<ErrorResponse> handleImageUploadFailed(ImageUploadFailedException ex) {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                .body(new ErrorResponse(
                        ex.getMessage(),
                        Instant.now(),
                        "IMAGE_UPLOAD_FAILED"
                ));
    }

    @ExceptionHandler(MunicipalityNotAssignedException.class)
    public ResponseEntity<ErrorResponse> handleMunicipalityNotAssigned(MunicipalityNotAssignedException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(
                        ex.getMessage(),
                        Instant.now(),
                        "MUNICIPALITY_NOT_ASSIGNED"
                ));
    }

    @ExceptionHandler(CannotConfirmOwnReportException.class)
    public ResponseEntity<ErrorResponse> handleCannotConfirmOwnReport(CannotConfirmOwnReportException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(
                        ex.getMessage(),
                        Instant.now(),
                        "CANNOT_CONFIRM_OWN_REPORT"
                ));
    }

    @ExceptionHandler(ReportAlreadyResolvedException.class)
    public ResponseEntity<ErrorResponse> handleReportAlreadyResolved(ReportAlreadyResolvedException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(
                        ex.getMessage(),
                        Instant.now(),
                        "REPORT_ALREADY_RESOLVED"
                ));
    }

    @ExceptionHandler(NotCommentOwnerException.class)
    public ResponseEntity<ErrorResponse> handleNotCommentOwner(NotCommentOwnerException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ErrorResponse(
                        ex.getMessage(),
                        Instant.now(),
                        "NOT_COMMENT_OWNER"
                ));
    }

    @ExceptionHandler(RequestNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleRequestNotFound(RequestNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(
                        ex.getMessage(),
                        Instant.now(),
                        "REQUEST_NOT_FOUND"
                ));
    }

    @ExceptionHandler(RequestNotPendingException.class)
    public ResponseEntity<ErrorResponse> handleRequestNotPending(RequestNotPendingException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(
                        ex.getMessage(),
                        Instant.now(),
                        "REQUEST_NOT_PENDING"
                ));
    }

    @ExceptionHandler(StripeOperationException.class)
    public ResponseEntity<ErrorResponse> handleStripeOperation(StripeOperationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                .body(new ErrorResponse(
                        ex.getMessage(),
                        Instant.now(),
                        "STRIPE_OPERATION_FAILED"
                ));
    }

    @ExceptionHandler(ReportNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleReportNotFound(ReportNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(
                        ex.getMessage(),
                        Instant.now(),
                        "REPORT_NOT_FOUND"
                ));
    }

    @ExceptionHandler(ModifyReportException.class)
    public ResponseEntity<ErrorResponse> handleModifyReport(ModifyReportException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ErrorResponse(
                        ex.getMessage(),
                        Instant.now(),
                        "MODIFY_REPORT_FORBIDDEN"
                ));
    }

    @ExceptionHandler(DuplicateRequestException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateRequest(DuplicateRequestException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(
                        ex.getMessage(),
                        Instant.now(),
                        "DUPLICATE_REQUEST"
                ));
    }

    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCommentNotFound(CommentNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(
                        ex.getMessage(),
                        Instant.now(),
                        "COMMENT_NOT_FOUND"
                ));
    }

    @ExceptionHandler(MunicipalityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleMunicipalityNotFound(MunicipalityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(
                        ex.getMessage(),
                        Instant.now(),
                        "MUNICIPALITY_NOT_FOUND"
                ));
    }

    @ExceptionHandler(SubscriptionNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleSubscriptionNotFound(SubscriptionNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(
                        ex.getMessage(),
                        Instant.now(),
                        "SUBSCRIPTION_NOT_FOUND"
                ));
    }

    @ExceptionHandler(WebhookProcessingException.class)
    public ResponseEntity<ErrorResponse> handleWebhookProcessing(WebhookProcessingException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(
                        ex.getMessage(),
                        Instant.now(),
                        "WEBHOOK_PROCESSING_ERROR"
                ));
    }

    @ExceptionHandler(MunicipalityResolutionException.class)
    public ResponseEntity<ErrorResponse> handleMunicipalityResolution(MunicipalityResolutionException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(
                        ex.getMessage(),
                        Instant.now(),
                        "MUNICIPALITY_RESOLUTION_ERROR"
                ));
    }
}
