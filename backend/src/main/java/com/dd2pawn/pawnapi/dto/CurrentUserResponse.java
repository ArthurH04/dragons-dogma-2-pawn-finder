package com.dd2pawn.pawnapi.dto;

import java.util.UUID;

import com.dd2pawn.pawnapi.model.enums.Role;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CurrentUserResponse {
    private UUID id;
    private String username;
    private String email;
    private Role role;
}