package com.dd2pawn.pawnapi.mapper;

import com.dd2pawn.pawnapi.dto.PawnRequest;
import com.dd2pawn.pawnapi.dto.PawnResponse;
import com.dd2pawn.pawnapi.model.Pawn;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PawnMapper {

    @Mapping(source = "inclinations", target = "inclinations")
    PawnResponse toResponse(Pawn pawn);

    @Mapping(source = "inclinations", target = "inclinations")
    Pawn toEntity(PawnRequest request);
}