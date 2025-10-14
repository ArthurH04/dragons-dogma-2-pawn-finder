package com.dd2pawn.pawnapi.service;

import com.dd2pawn.pawnapi.dto.PawnRequest;
import com.dd2pawn.pawnapi.exception.DuplicateEntryException;
import com.dd2pawn.pawnapi.mapper.PawnMapper;
import com.dd2pawn.pawnapi.model.Pawn;
import com.dd2pawn.pawnapi.model.User;
import com.dd2pawn.pawnapi.model.enums.*;
import com.dd2pawn.pawnapi.repository.PawnRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static com.dd2pawn.pawnapi.repository.PawnSpecs.*;

@Service
@AllArgsConstructor
public class PawnService {

    private final PawnRepository pawnRepository;
    private final PawnMapper pawnMapper;

    public Pawn save(PawnRequest pawnRequest, User user) {
    	
    	if(pawnRepository.findByPawnId(pawnRequest.getPawnId()).isPresent()) {
    		throw new DuplicateEntryException("pawnId", "pawnId is already in use");
    	}
    	
        Pawn pawn = pawnMapper.toEntity(pawnRequest);
        pawn.setUser(user);
        return pawnRepository.save(pawn);
    }

    public Optional<Pawn> findById(UUID id) {
        return pawnRepository.findById(id);
    }

    public Optional<Pawn> findByPawnId(String id) {
        return pawnRepository.findByPawnId(id);
    }

    public void delete(UUID pawnId, User user) {
        Pawn pawn = pawnRepository.findById(pawnId)
                .orElseThrow(() -> new EntityNotFoundException("Pawn not found"));

        if (!pawn.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("You cannot delete another user's pawn");
        }

        pawnRepository.delete(pawn);
    }

    public Page<Pawn> getPawns(String name, Integer level, String platform, String gender, Integer page, Integer size) {
        Specification<Pawn> specs = (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();

        if (name != null && !name.isEmpty()) {
            specs = specs.and(nameLike(name));
        }

        if (level != null && level > 1) {
            specs = specs.and(levelEquals(level));
        }

        if (platform != null && !platform.isEmpty()) {
            specs = specs.and(platformLike(platform));
        }

        if (gender != null && !gender.isEmpty()) {
            specs = specs.and(genderEqual(gender));
        }
        Pageable pageRequest = PageRequest.of(page, size);
        return pawnRepository.findAll(specs, pageRequest);
    }

    public Pawn updatePawn(UUID pawnId, PawnRequest pawnRequest, User user) {
        Pawn pawn = pawnRepository.findById(pawnId)
                .orElseThrow(() -> new EntityNotFoundException("Pawn not found"));

        if (!pawn.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("You cannot update another user's pawn");
        }

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
        if (!pawn.getPawnId().equals(pawnRequest.getPawnId())) {
            if (pawnRepository.findByPawnId(pawnRequest.getPawnId()).isPresent()) {
                throw new IllegalArgumentException("pawnId already exists");
            }
            pawn.setPawnId(pawnRequest.getPawnId());
        }
        return pawnRepository.save(pawn);
    }
}
