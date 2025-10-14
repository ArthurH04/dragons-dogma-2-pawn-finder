package com.dd2pawn.pawnapi.repository;

import com.dd2pawn.pawnapi.model.Pawn;
import com.dd2pawn.pawnapi.model.enums.Gender;
import com.dd2pawn.pawnapi.model.enums.Platform;

import org.springframework.data.jpa.domain.Specification;

public class PawnSpecs {

    public static Specification<Pawn> nameLike(String name) {
        return (root, query, cb) ->
                cb.like(cb.upper(root.get("name")), "%" + name.toUpperCase() + "%");
    }

    public static Specification<Pawn> levelEquals(Integer level) {
        return (root, query, cb) -> cb.equal(root.get("level"), level);
    }

    public static Specification<Pawn> platformLike(String platform) {
        return (root, query, cb) ->
                cb.equal(root.get("platform"), Platform.valueOf(platform.toUpperCase()));
    }
    public static Specification<Pawn> genderEqual(String gender) {
        return (root, query, cb) -> cb.equal(root.get("gender"), Gender.valueOf(gender.toUpperCase()));
    }
}
