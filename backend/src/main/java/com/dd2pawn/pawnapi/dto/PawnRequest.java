package com.dd2pawn.pawnapi.dto;

import com.dd2pawn.pawnapi.model.enums.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class PawnRequest {

    @NotBlank(message = "Pawn ID cannot be empty")

    private String pawnId;

    @NotBlank(message = "Name cannot be empty")
    private String name;

    @NotNull(message = "Gender cannot be empty")
    private Gender gender;

    @Min(value = 1, message = "Level must be at least {value}")
    @Max(value = 999, message = "Level must not be greater than {value}")
    private Integer level;

    @NotNull(message = "Vocation cannot be empty")
    private Vocations vocations;

    @NotNull(message = "Inclination cannot be empty")
    private Inclinations inclinations;

    @NotNull(message = "Specialization cannot be empty")
    private Specializations specializations;

    @NotBlank(message = "Notes cannot be empty")
    private String notes;

    private String imageUrl;

    @NotNull(message = "Platform cannot be empty")
    private Platform platform;

    @NotBlank(message = "Platform identifier url cannot be empty")
    private String platformIdentifier;
}
