package com.dd2pawn.pawnapi.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dd2pawn.pawnapi.model.PasswordResetToken;
import com.dd2pawn.pawnapi.model.User;
import com.dd2pawn.pawnapi.repository.PasswordResetTokenRepository;
import com.dd2pawn.pawnapi.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PasswordReset {
    private final UserRepository userRepository;
    private final PasswordResetTokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailServiceImpl emailService;

    public static final long EXPIRATION_MINUTES = 30;

    @Transactional
    public void forgotPassword(String email) {
        User user = userRepository.findByEmail(email.toLowerCase())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        System.out.println("email: "+ email);

        UUID token = UUID.randomUUID();

        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setUser(user);
        resetToken.setToken(token);
        resetToken.setExpiresAt(LocalDateTime.now().plusMinutes(EXPIRATION_MINUTES));
        resetToken.setUsed(false);

        tokenRepository.save(resetToken);
        emailService.sendPasswordResetEmail(user.getEmail(), token.toString());
    }

    @Transactional
    public void resetPassword(String tokenStr, String newPassword) {
        UUID tokenUUID = UUID.fromString(tokenStr);

        PasswordResetToken token = tokenRepository.findByToken(tokenUUID)
                .orElseThrow(() -> new RuntimeException("Invalid token"));

        if (token.isUsed() || token.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token expired or already used");
        }

        User user = token.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        token.setUsed(true);
        tokenRepository.save(token);
    }
}
