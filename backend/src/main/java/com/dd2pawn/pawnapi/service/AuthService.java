package com.dd2pawn.pawnapi.service;

import com.dd2pawn.pawnapi.dto.RegisterRequest;
import com.dd2pawn.pawnapi.exception.DuplicateEntryException;
import com.dd2pawn.pawnapi.exception.InvalidCredentialsException;
import com.dd2pawn.pawnapi.dto.AuthenticationRequest;
import com.dd2pawn.pawnapi.dto.AuthenticationResponse;
import com.dd2pawn.pawnapi.model.Token;
import com.dd2pawn.pawnapi.model.User;
import com.dd2pawn.pawnapi.model.enums.Role;
import com.dd2pawn.pawnapi.repository.TokenRepository;
import com.dd2pawn.pawnapi.repository.UserRepository;
import com.dd2pawn.pawnapi.security.filter.AuthRateLimitFilter;
import com.dd2pawn.pawnapi.security.jwt.JwtUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
        private final UserRepository userRepository;
        private final PasswordEncoder passwordEncoder;
        private final JwtUtils jwtService;
        private final AuthenticationManager authenticationManager;
        private final TokenRepository tokenRepository;
        private final AuthRateLimitFilter authRateLimitFilter;
        private final LoginRateLimitService loginRateLimitService;
        private final HttpServletRequest httpRequest;


        public AuthenticationResponse register(RegisterRequest request) {

                if (userRepository.existsByEmail(request.getEmail())) {
                        throw new DuplicateEntryException("email", "Email is already in use");
                }

                if (userRepository.existsByUsername(request.getUsername())) {
                        throw new DuplicateEntryException("username", "Username is already in use");
                }

                User user = User.builder().username(request.getUsername())
                                .email(request.getEmail().toLowerCase())
                                .password(passwordEncoder.encode(request.getPassword()))
                                .role(Role.USER)
                                .build();

                userRepository.save(user);
                String accessToken = jwtService.generateToken(user);
                String refreshToken = jwtService.generateRefreshToken(user);
                saveUserToken(accessToken, refreshToken, user);
                return AuthenticationResponse.builder()
                                .token(accessToken)
                                .refreshToken(refreshToken)
                                .build();
        }

        public AuthenticationResponse authenticate(AuthenticationRequest request) {

                String ip = authRateLimitFilter.getClientIp(httpRequest);
                loginRateLimitService.checkBlockedIp(ip);

                try {
                        authenticationManager.authenticate(
                                        new UsernamePasswordAuthenticationToken(
                                                        request.getEmail(),
                                                        request.getPassword()));
                } catch (Exception ex) {

                        
                        System.out.println("Failed login attempt from IP: " + ip);
                        loginRateLimitService.consumeFailedAttempt(ip);


                        throw new InvalidCredentialsException("Invalid email or password");
                }
                User user = userRepository.findByEmail(request.getEmail())
                                .orElseThrow();

                String accessToken = jwtService.generateToken(user);
                String jwtRefreshExpiration = jwtService.generateRefreshToken(user);

                revokeAllUserTokens(user);
                saveUserToken(accessToken, jwtRefreshExpiration, user);
                return AuthenticationResponse.builder()
                                .token(accessToken)
                                .refreshToken(jwtRefreshExpiration)
                                .build();
        }

        public ResponseEntity<AuthenticationResponse> refreshToken(HttpServletRequest request, HttpServletResponse response)
                        throws IOException {
                final String authHeader = request.getHeader("Authorization");

                if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
                }

                String token = authHeader.substring(7);
                String userEmail = jwtService.extractUsername(token);
                User user = userRepository.findByEmail(userEmail)
                                .orElseThrow(() -> new RuntimeException("No user found"));

                if (jwtService.isRefreshTokenValid(token, user)) {
                        String accessToken = jwtService.generateToken(user);
                        String refreshToken = jwtService.generateRefreshToken(user);

                        revokeAllUserTokens(user);
                        saveUserToken(accessToken, refreshToken, user);
                        return ResponseEntity.ok(AuthenticationResponse.builder().token(accessToken).refreshToken(refreshToken).build());
                }
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);

        }

        private void saveUserToken(String accessToken, String refreshToken, User user) {
                Token token = new Token();
                token.setAccessToken(accessToken);
                token.setRefreshToken(refreshToken);
                token.setLoggedOut(false);
                token.setUser(user);
                tokenRepository.save(token);
        }

        private void revokeAllUserTokens(User user) {
                List<Token> validUserTokens = tokenRepository.findAllAccessTokensByUser(user.getId());
                if (validUserTokens.isEmpty())
                        return;

                validUserTokens.forEach(token -> {
                        token.setLoggedOut(true);
                });
                tokenRepository.saveAll(validUserTokens);
        }
}