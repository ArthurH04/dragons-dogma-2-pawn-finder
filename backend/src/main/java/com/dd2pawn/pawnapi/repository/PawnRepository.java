package com.dd2pawn.pawnapi.repository;

import com.dd2pawn.pawnapi.model.Pawn;
import com.dd2pawn.pawnapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PawnRepository extends JpaRepository<Pawn, UUID>, JpaSpecificationExecutor<Pawn> {
    boolean existsByUser(User user);
    Optional<Pawn> findByPawnId(String pawnId);
}
