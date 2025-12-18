package com.dd2pawn.pawnapi.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.dd2pawn.pawnapi.dto.CustomFieldError;
import com.dd2pawn.pawnapi.dto.ResponseError;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseError handleAccessDenied(AccessDeniedException ex) {
        return new ResponseError(403, "Access Denied", List.of(new CustomFieldError("access", ex.getMessage())));
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseError handleInvalidCredentials(InvalidCredentialsException ex) {
        return new ResponseError(401, "Invalid Credentials",
                List.of(new CustomFieldError("credentials", ex.getMessage())));
    }

     @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseError handleValidation(MethodArgumentNotValidException ex) {
        List<CustomFieldError> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> new CustomFieldError(err.getField(), err.getDefaultMessage()))
                .collect(Collectors.toList());

        return new ResponseError(422, "Validation error", errors);
    }

    @ExceptionHandler(MultipleFieldErrorsException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseError handleMultipleFieldErrors(MultipleFieldErrorsException ex) {
        return new ResponseError(422, "Validation error", ex.getErrors());
    }

    @ExceptionHandler(DuplicateEntryException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseError handleDuplicateEntry(DuplicateEntryException ex) {
        return new ResponseError(409, "Duplicate Entry", List.of(new CustomFieldError(ex.getField(), ex.getMessage())));
    }

    @ExceptionHandler(TooManyLoginAttemptsException.class)
    @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
    public ResponseError handleTooManyLoginAttempts(TooManyLoginAttemptsException ex) {
        return new ResponseError(429, "Too Many Requests", List.of(new CustomFieldError("login", ex.getMessage())));
    }
}
