package com.dd2pawn.pawnapi.exception;

public class InvalidCredentialsException extends RuntimeException{
    public InvalidCredentialsException (String message) {
        super(message);
    }
}
