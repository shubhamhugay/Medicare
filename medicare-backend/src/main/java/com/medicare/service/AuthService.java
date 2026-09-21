package com.medicare.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.medicare.dto.AuthResponse;
import com.medicare.dto.LoginRequest;
import com.medicare.dto.RegisterRequest;
import com.medicare.dto.UserResponse;
import com.medicare.entity.Role;
import com.medicare.entity.User;
import com.medicare.exception.EmailAlreadyExistsException;
import com.medicare.exception.UserNotFoundException;
import com.medicare.repository.UserRepository;
import com.medicare.security.JwtService;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtService jwtService) {

        this.userRepository =
                userRepository;

        this.passwordEncoder =
                passwordEncoder;

        this.authenticationManager =
                authenticationManager;

        this.jwtService =
                jwtService;
    }

    public UserResponse register(
            RegisterRequest request) {

        String email =
                request.getEmail()
                        .trim()
                        .toLowerCase();

        if (userRepository
                .existsByEmailIgnoreCase(email)) {

            throw new EmailAlreadyExistsException(
                    "Email is already registered"
            );
        }

        User user =
                new User();

        user.setName(
                request.getName().trim()
        );

        user.setEmail(email);

        user.setPhone(
                request.getPhone()
        );

        user.setPassword(
                passwordEncoder.encode(
                        request.getPassword()
                )
        );

        /*
         * Public registration always creates
         * a PATIENT account.
         */
        user.setRole(
                Role.PATIENT
        );

        User savedUser =
                userRepository.save(user);

        return mapToUserResponse(
                savedUser
        );
    }

    public AuthResponse login(
            LoginRequest request) {

        String email =
                request.getEmail()
                        .trim()
                        .toLowerCase();

        /*
         * AuthenticationManager checks
         * email and password.
         */
        var authentication =
                authenticationManager
                        .authenticate(
                                new UsernamePasswordAuthenticationToken(
                                        email,
                                        request.getPassword()
                                )
                        );

        UserDetails userDetails =
                (UserDetails)
                        authentication
                                .getPrincipal();

        User user =
                userRepository
                        .findByEmailIgnoreCase(
                                userDetails
                                        .getUsername()
                        )
                        .orElseThrow(() ->
                                new UserNotFoundException(
                                        "User not found"
                                )
                        );

        String token =
                jwtService.generateToken(
                        userDetails
                );

        return new AuthResponse(
                token,
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole()
        );
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