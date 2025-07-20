package com.dd2pawn.pawnapi.controller;

import com.dd2pawn.pawnapi.dto.UserRequest;
import com.dd2pawn.pawnapi.dto.UserResponse;
import com.dd2pawn.pawnapi.mapper.UserMapper;
import com.dd2pawn.pawnapi.model.User;
import com.dd2pawn.pawnapi.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable("id") String id){
        UUID userId = UUID.fromString(id);
        return userService.findById(userId)
                .map(userMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Void> saveUser(@RequestBody @Valid UserRequest userRequest){
        User userEntity = userService.save(userRequest);
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(userEntity.getId()).toUri()).build();
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
}
