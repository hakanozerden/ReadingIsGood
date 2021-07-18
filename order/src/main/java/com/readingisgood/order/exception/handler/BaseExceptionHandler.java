package com.readingisgood.order.exception.handler;

import com.readingisgood.order.constants.errors.Error;
import com.readingisgood.order.exception.BusinessException;
import com.readingisgood.order.exception.EntityNotFoundException;
import com.readingisgood.order.exception.InvalidRequestException;
import com.readingisgood.order.exception.UnauthorizedException;
import com.readingisgood.order.response.ErrorResponse;
import com.readingisgood.order.response.ResponseBuilder;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/** @author hakan.ozerden */
@RestControllerAdvice
@Slf4j
public class BaseExceptionHandler {

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ErrorResponse> invalidRequestExceptionHandler(
            InvalidRequestException exception) {
        log.error("Bad request!.", exception);
        return ResponseBuilder.build(
                new ErrorResponse(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> baseExceptionHandler(Exception exception) {
        log.error("An unexpected error has occurred. Detail:", exception);
        return ResponseBuilder.build(
                new ErrorResponse(Error.SYSTEM_ERROR.getDescription()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> businessExceptionHandler(BusinessException exception) {
        log.error("Business exception has occurred. Detail:", exception);
        return ResponseBuilder.build(
                new ErrorResponse(exception.getMessage()), HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<ErrorResponse> signatureExceptionHandler(SignatureException exception) {
        log.error("Invalid JWT signature. Detail:", exception);
        return ResponseBuilder.build(
                new ErrorResponse(Error.INVALID_JWT_SIGNATURE.getDescription()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<ErrorResponse> mapformedJwtException(MalformedJwtException exception) {
        log.error("Invalid JWT. Detail:", exception);
        return ResponseBuilder.build(
                new ErrorResponse(Error.INVALID_JWT.getDescription()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ErrorResponse> expiredJwtExceptionHandler(ExpiredJwtException exception) {
        log.error("Expired JWT. Detail:", exception);
        return ResponseBuilder.build(
                new ErrorResponse(Error.JWT_EXPIRED.getDescription()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnsupportedJwtException.class)
    public ResponseEntity<ErrorResponse> unsupportedJwtExceptionHandler(
            UnsupportedJwtException exception) {
        log.error("JWT not supported. Detail:", exception);
        return ResponseBuilder.build(
                new ErrorResponse(Error.JWT_NOT_SUPPORTED.getDescription()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> unauthorizedExceptionHandler(
            UnauthorizedException exception) {
        log.error("Unauthorized. Detail:", exception);
        return ResponseBuilder.build(
                new ErrorResponse(exception.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> badCredentialsExceptionHandler(
            BadCredentialsException exception) {
        log.error("Validation failed. Detail:", exception);
        return ResponseBuilder.build(
                new ErrorResponse(Error.VALIDATION_FAILED.getDescription()),
                HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> entityNotFoundExceptionHandler(
            EntityNotFoundException exception) {
        log.error("Entity not found. Detail:", exception);
        return ResponseBuilder.build(
                new ErrorResponse(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleInvalidArgumentException(
            MethodArgumentNotValidException exception) {
        log.error("Entity not found with given id. Detail:", exception);

        String message =
                exception.getBindingResult().getFieldErrors().stream()
                        .map(
                                fieldError ->
                                        fieldError.getField()
                                                + " "
                                                + fieldError.getDefaultMessage())
                        .collect(Collectors.joining(" & "));

        return ResponseBuilder.build(new ErrorResponse(message), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<ErrorResponse> duplicateKeyExceptionHandler(
            DuplicateKeyException exception) {
        log.error("Duplicate key. Detail:", exception);

        Pattern logEntry = Pattern.compile("\\{(.*?)\\}");
        Matcher matchPattern = logEntry.matcher(exception.getLocalizedMessage());

        return ResponseBuilder.build(
                new ErrorResponse(
                        "%s already exists!"
                                .replaceAll(
                                        "%s",
                                        matchPattern.find() ? matchPattern.group(1).trim() : "")),
                HttpStatus.BAD_REQUEST);
    }
}
