package com.dd2pawn.pawnapi.service;

import com.dd2pawn.pawnapi.dto.UserRequest;
import com.dd2pawn.pawnapi.exceptions.DuplicateEntryException;
import com.dd2pawn.pawnapi.exceptions.OperationNotAllowedException;
import com.dd2pawn.pawnapi.mapper.UserMapper;
import com.dd2pawn.pawnapi.model.Pawn;
import com.dd2pawn.pawnapi.model.User;
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
    private final UserMapper pawnMapper;
    private final PasswordEncoder passwordEncoder;

    public Optional<User> findById(UUID id) {
        return userRepository.findById(id);
    }

    public User save(UserRequest userRequest) {
        String password = userRequest.getPassword();
        userRequest.setPassword(passwordEncoder.encode(password));

        boolean exists = userRepository.existsByUsername(userRequest.getUsername());
        if(exists) {
            throw new DuplicateEntryException("User already exists with login: " + userRequest.getUsername());
        }
        User user = pawnMapper.toEntity(userRequest);
        return userRepository.save(user);
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
