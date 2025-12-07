package com.dd2pawn.pawnapi.service;

public interface EmailService {
    void sendPasswordResetEmail(String to, String token);
}
