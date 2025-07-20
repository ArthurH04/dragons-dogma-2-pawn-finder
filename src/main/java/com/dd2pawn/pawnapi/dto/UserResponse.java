package com.dd2pawn.pawnapi.dto;

import com.dd2pawn.pawnapi.model.Pawn;
import com.dd2pawn.pawnapi.model.enums.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class UserResponse {
    private UUID id;
    private String username;
    private List<PawnResponse> pawns;
}
