package com.dd2pawn.pawnapi.dto;

import com.dd2pawn.pawnapi.model.enums.*;
import lombok.Data;

import java.util.UUID;

@Data
public class PawnResponse {
    private UUID id;
    private String pawnId;
    private String name;
    private Gender gender;
    private Integer level;
    private Vocations vocations;
    private Inclinations inclinations;
    private Specializations specializations;
    private String notes;
    private String imageUrl;
    private Platform platform;
    private String platformIdentifier;
}
