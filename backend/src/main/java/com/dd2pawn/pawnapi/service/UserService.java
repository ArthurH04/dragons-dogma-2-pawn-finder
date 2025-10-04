package com.dd2pawn.pawnapi.service;

import com.dd2pawn.pawnapi.dto.UserRequest;
import com.dd2pawn.pawnapi.exception.DuplicateEntryException;
import com.dd2pawn.pawnapi.exception.OperationNotAllowedException;
import com.dd2pawn.pawnapi.mapper.UserMapper;
import com.dd2pawn.pawnapi.model.User;
import com.dd2pawn.pawnapi.model.enums.Role;
import com.dd2pawn.pawnapi.repository.PawnRepository;
import com.dd2pawn.pawnapi.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PawnRepository pawnRepository;

    public Optional<User> findById(UUID id) {
        return userRepository.findById(id);
    }
    
    public Optional<User> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

    public void delete(User user) {
        if (hasAPawn(user)){
            throw new OperationNotAllowedException("The user has registered pawns");
        }
        userRepository.delete(user);
    }

    public boolean hasAPawn(User user) {
        return pawnRepository.existsByUser(user);
    }

}
