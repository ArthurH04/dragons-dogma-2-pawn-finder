package com.dd2pawn.pawnapi.controller;

import com.dd2pawn.pawnapi.dto.UserRequest;
import com.dd2pawn.pawnapi.dto.UserResponse;
import com.dd2pawn.pawnapi.mapper.UserMapper;
import com.dd2pawn.pawnapi.model.User;
import com.dd2pawn.pawnapi.repository.UserRepository;
import com.dd2pawn.pawnapi.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable("id") String id){
        UUID userId = UUID.fromString(id);
        return userService.findById(userId)
                .map(userMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable("id") String id){
        UUID userId = UUID.fromString(id);
        return userService.findById(userId)
                .map(pawn -> {
                    userService.delete(pawn);
                    return ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/me")
    public String getCurrentUser(Authentication authentication){
       return userService.getCurrentUser(authentication);
    }
}
