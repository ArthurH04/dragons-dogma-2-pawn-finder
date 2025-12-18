package com.dd2pawn.pawnapi.exception;

public class TooManyLoginAttemptsException extends RuntimeException {
    public TooManyLoginAttemptsException() {
        super("Too many failed login attempts. Please try again later.");
    }
}
