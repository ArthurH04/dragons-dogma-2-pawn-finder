package com.dd2pawn.pawnapi.dto;

public record ResetPasswordRequest(String token, String newPassword) {
    
}
