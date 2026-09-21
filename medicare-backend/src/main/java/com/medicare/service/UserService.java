package com.medicare.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.medicare.dto.UserRequest;
import com.medicare.dto.UserResponse;
import com.medicare.entity.User;
import com.medicare.exception.UserNotFoundException;
import com.medicare.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponse createUser(
            UserRequest request) {

        if (userRepository.existsByEmail(
                request.getEmail())) {

            throw new IllegalArgumentException(
                    "Email is already registered"
            );
        }

        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());

        /*
         * Never store a plain-text password.
         * BCrypt converts it into a secure hash.
         */
        user.setPassword(
                passwordEncoder.encode(
                        request.getPassword()
                )
        );

        user.setRole(request.getRole());

        User savedUser =
                userRepository.save(user);

        return mapToUserResponse(savedUser);
    }

    public List<UserResponse> getAllUsers() {

        List<User> users =
                userRepository.findAll();

        List<UserResponse> responses =
                new ArrayList<>();

        for (User user : users) {

            responses.add(
                    mapToUserResponse(user)
            );
        }

        return responses;
    }

    public UserResponse getUserById(Long id) {

        User user = userRepository
                .findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found with id: " + id
                        )
                );

        return mapToUserResponse(user);
    }

    private UserResponse mapToUserResponse(
            User user) {

        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhone(),
                user.getRole()
        );
    }
}