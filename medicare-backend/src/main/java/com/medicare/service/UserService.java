package com.medicare.service;

import org.springframework.stereotype.Service;

import com.medicare.dto.UserResponse;
import com.medicare.entity.User;
import com.medicare.exception.UserNotFoundException;
import com.medicare.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(
            UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    public UserResponse getCurrentUser(
            String email) {

        User user =
                userRepository
                        .findByEmailIgnoreCase(email)
                        .orElseThrow(() ->
                                new UserNotFoundException(
                                        "User not found"
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