package com.dd2pawn.pawnapi.exception;

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
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
	public ResponseError handleValidation(MethodArgumentNotValidException e) {
	    List<CustomFieldError> errors = e.getFieldErrors().stream()
	        .map(fe -> new CustomFieldError(fe.getField(), fe.getDefaultMessage()))
	        .collect(Collectors.toList());

	    return new ResponseError(422, "Validation error", errors);
	}
	
	@ExceptionHandler(DuplicateEntryException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public ResponseError handleDuplicateEntryException(DuplicateEntryException ex) {
		return new ResponseError(409, "Duplicate Entry", List.of(new CustomFieldError(ex.getField(), ex.getMessage())));
	}	
}
