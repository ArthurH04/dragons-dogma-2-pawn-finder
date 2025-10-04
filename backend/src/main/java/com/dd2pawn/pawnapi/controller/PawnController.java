package com.dd2pawn.pawnapi.controller;

import com.dd2pawn.pawnapi.dto.PawnRequest;
import com.dd2pawn.pawnapi.dto.PawnResponse;
import com.dd2pawn.pawnapi.mapper.PawnMapper;
import com.dd2pawn.pawnapi.model.Pawn;
import com.dd2pawn.pawnapi.model.User;
import com.dd2pawn.pawnapi.model.enums.Gender;
import com.dd2pawn.pawnapi.service.PawnService;
import com.dd2pawn.pawnapi.service.UserService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.UUID;


@RestController
@RequestMapping("/api/pawns")
@AllArgsConstructor
public class PawnController {

    private final PawnService pawnService;
    private final PawnMapper pawnMapper;
    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<PawnResponse> getPawn(@PathVariable("id") String id){
        UUID pawnId = UUID.fromString(id);
        return pawnService.findById(pawnId)
                .map(pawnMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/by-pawn-id/{pawnId}")
    public ResponseEntity<PawnResponse> getByPawnId(@PathVariable("pawnId") String pawnId) {
        return pawnService.findByPawnId(pawnId)
                .map(pawnMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    

    @GetMapping
    public ResponseEntity<Page<PawnResponse>> getPawns(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "level", required = false) Integer level,
            @RequestParam(value = "platform", required = false) String platform,
            @RequestParam(value = "gender", required = false) Gender gender,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {
        Page<Pawn> result = pawnService.getPawns(name, level, platform, gender, page, size);
        Page<PawnResponse> map = result.map(pawnMapper::toResponse);
        return ResponseEntity.ok(map);
    }

    @PostMapping
    public ResponseEntity<Void> savePawn(@RequestBody @Valid PawnRequest pawn, @AuthenticationPrincipal UserDetails userDetails){
    	User user = userService.findByEmail(userDetails.getUsername()).orElseThrow(() -> new RuntimeException("User not found"));
        Pawn pawnEntity = pawnService.save(pawn, user);
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(pawnEntity.getId()).toUri()).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<PawnResponse> updatePawn(@PathVariable("id") String id, @RequestBody @Valid PawnRequest pawnRequest, @AuthenticationPrincipal UserDetails userDetails){
        UUID pawnId = UUID.fromString(id);
        User user = userService.findByEmail(userDetails.getUsername()).orElseThrow(() -> new RuntimeException("User not found"));
        pawnService.updatePawn(pawnId, pawnRequest, user);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") UUID id, @AuthenticationPrincipal UserDetails userDetails){
        User user = userService.findByEmail(userDetails.getUsername()).orElseThrow(() -> new RuntimeException("User not found"));
        pawnService.delete(id, user);
        return ResponseEntity.noContent().build();
    }
}