package com.dd2pawn.pawnapi.model;

import com.dd2pawn.pawnapi.model.enums.*;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "pawns", indexes = {
        @Index(name = "idx_pawn_level", columnList = "level"),
        @Index(name = "idx_pawn_platform", columnList = "platform"),
})
@Data
public class Pawn extends BaseEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "pawn_id", length = 50, nullable = false)
    private String pawnId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(nullable = false)
    private Integer level;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Vocations vocations;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Inclinations inclinations;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Specializations specializations;

    private String notes;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Platform platform;

    @Column(name = "platform_identifier", length = 100, nullable = false)
    private String platformIdentifier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
