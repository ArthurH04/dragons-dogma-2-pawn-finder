package com.dd2pawn.pawnapi.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dd2pawn.pawnapi.model.Token;

public interface TokenRepository extends JpaRepository<Token, Integer>{


    @Query("""
            SELECT t from Token t
            INNER JOIN User u on t.user.id = u.id
            WHERE t.user.id = :userId AND t.loggedOut = false
            """)
    List<Token> findAllAccessTokensByUser(UUID userId);
    Optional<Token> findByAccessToken(String accessToken);
    Optional<Token> findByRefreshToken(String refreshToken);
}
