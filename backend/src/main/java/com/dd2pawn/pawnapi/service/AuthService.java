package com.dd2pawn.pawnapi.service;

import com.dd2pawn.pawnapi.dto.RegisterRequest;
import com.dd2pawn.pawnapi.exception.DuplicateEntryException;
import com.dd2pawn.pawnapi.exception.InvalidCredentialsException;
import com.dd2pawn.pawnapi.dto.AuthenticationRequest;
import com.dd2pawn.pawnapi.dto.AuthenticationResponse;
import com.dd2pawn.pawnapi.model.User;
import com.dd2pawn.pawnapi.model.enums.Role;
import com.dd2pawn.pawnapi.repository.UserRepository;
import com.dd2pawn.pawnapi.security.jwt.JwtUtils;

import lombok.RequiredArgsConstructor;

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

    public AuthenticationResponse register(RegisterRequest request) {
    	
    	if(userRepository.existsByEmail(request.getEmail())) {
    		throw new DuplicateEntryException("email", "Email is already in use");
    	}
    	
    	if(userRepository.existsByUsername(request.getUsername())) {
    		throw new DuplicateEntryException("username", "Username is already in use");
    	}
    	
        User user = User.builder().username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        try{
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));
        }catch(Exception ex){
        	throw new InvalidCredentialsException("Invalid email or password");
        }
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
