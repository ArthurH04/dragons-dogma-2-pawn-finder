package com.dd2pawn.pawnapi.dto;

import jakarta.validation.constraints.NotBlank;

public record ChangePasswordRequest(@NotBlank(message = "Current password is required") String currentPassword,
        @NotBlank(message = "New password is required") String newPassword,
        @NotBlank(message = "Password confirmation is required") String confirmNewPassword) {

    public boolean passwordsMatch() {
        return newPassword != null && newPassword.equals(confirmNewPassword);
    }
}