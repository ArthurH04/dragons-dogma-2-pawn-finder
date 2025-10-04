
package com.dd2pawn.pawnapi.controller;

import com.dd2pawn.pawnapi.dto.RegisterRequest;
import com.dd2pawn.pawnapi.dto.AuthenticationRequest;
import com.dd2pawn.pawnapi.dto.AuthenticationResponse;
import com.dd2pawn.pawnapi.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }
}
