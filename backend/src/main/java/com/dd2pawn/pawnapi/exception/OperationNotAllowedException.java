package com.dd2pawn.pawnapi.exception;

public class OperationNotAllowedException extends RuntimeException{
    public OperationNotAllowedException(String message) {
        super(message);
    }
}
