package com.dd2pawn.pawnapi.service;

import com.dd2pawn.pawnapi.dto.PawnRequest;
import com.dd2pawn.pawnapi.mapper.PawnMapper;
import com.dd2pawn.pawnapi.model.Pawn;
import com.dd2pawn.pawnapi.model.User;
import com.dd2pawn.pawnapi.model.enums.*;
import com.dd2pawn.pawnapi.repository.PawnRepository;
import com.dd2pawn.pawnapi.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static com.dd2pawn.pawnapi.repository.PawnSpecs.*;

@Service
@AllArgsConstructor
public class PawnService {

    private final PawnRepository pawnRepository;
    private final PawnMapper pawnMapper;
    private final UserRepository userRepository;

    public Pawn save(PawnRequest pawnRequest){
        Pawn pawn = pawnMapper.toEntity(pawnRequest);
        User user = userRepository.findById(pawnRequest.getUserId()).orElseThrow(() -> new EntityNotFoundException("User not found"));
        pawn.setUser(user);
        return pawnRepository.save(pawn);
    }

    public Optional<Pawn> findById(UUID id) {
        return pawnRepository.findById(id);
    }

    public Optional<Pawn> findByPawnId(String id) {
        return pawnRepository.findByPawnId(id);
    }

    public void delete(Pawn entity) {pawnRepository.delete(entity);
    }

    public Page<Pawn> getPawns(String name, Integer level, String platform, Gender gender, Integer page, Integer size) {
        Specification<Pawn> specs = (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();

        if(name != null && !name.isEmpty()){
            specs = specs.and(nameLike(name));
        }

        if(level != null && level > 1){
            specs = specs.and(levelEquals(level));
        }

        if(platform != null && !platform.isEmpty()){
            specs = specs.and(platformLike(name));
        }

        if(gender != null && !gender.name().isEmpty()){
            specs = specs.and(genderEqual(gender));
        }
        Pageable pageRequest = PageRequest.of(page, size);
        return pawnRepository.findAll(specs, pageRequest);
    }

    public Pawn updatePawn(UUID id, PawnRequest pawnRequest) {
        Optional<Pawn> pawnOptional = pawnRepository.findById(id);

        if (pawnOptional.isEmpty()) {
            throw new EntityNotFoundException("Pawn with id " + id + " not found.");
        }

        User user = userRepository.findById(pawnRequest.getUserId()).orElseThrow(() -> new EntityNotFoundException("User with id " + pawnRequest.getUserId() + " not found."));
        Pawn pawn = getPawn(pawnRequest, pawnOptional, user);
        return pawnRepository.save(pawn);
    }

    private static Pawn getPawn(PawnRequest pawnRequest, Optional<Pawn> pawnOptional, User user) {
        Pawn pawn = pawnOptional.get();
        pawn.setName(pawnRequest.getName());
        pawn.setGender(pawnRequest.getGender());
        pawn.setLevel(pawnRequest.getLevel());
        pawn.setVocations(pawnRequest.getVocations());
        pawn.setInclinations(pawnRequest.getInclinations());
        pawn.setSpecializations(pawnRequest.getSpecializations());
        pawn.setNotes(pawnRequest.getNotes());
        pawn.setImageUrl(pawnRequest.getImageUrl());
        pawn.setPlatform(pawnRequest.getPlatform());
        pawn.setPlatformIdentifier(pawnRequest.getPlatformIdentifier());
        pawn.setUser(user);
        return pawn;
    }
}
