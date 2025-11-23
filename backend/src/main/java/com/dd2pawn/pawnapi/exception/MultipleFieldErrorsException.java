package com.dd2pawn.pawnapi.exception;

import java.util.List;

import com.dd2pawn.pawnapi.dto.CustomFieldError;

public class MultipleFieldErrorsException extends RuntimeException {
    
    private final List<CustomFieldError> errors;

    /**
     * Construtor
     * @param errors Lista de erros de validação (campo + mensagem)
     */
    public MultipleFieldErrorsException(List<CustomFieldError> errors) {
        super("Multiple field errors"); // mensagem genérica, não usada diretamente
        this.errors = errors;
    }

    /**
     * Retorna a lista de erros
     */
    public List<CustomFieldError> getErrors() {
        return errors;
    }
}
