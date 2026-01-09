package com.dd2pawn.pawnapi.mapper;

import com.dd2pawn.pawnapi.dto.UserRequest;
import com.dd2pawn.pawnapi.dto.UserResponse;
import com.dd2pawn.pawnapi.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponse toResponse(User user);
    
    User toEntity(UserRequest request);
}