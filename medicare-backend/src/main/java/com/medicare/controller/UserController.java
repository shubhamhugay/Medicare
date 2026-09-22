package com.medicare.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medicare.dto.UserResponse;
import com.medicare.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(
            UserService userService) {

        this.userService = userService;
    }


    // -------------------------------------------------
    // CURRENT LOGGED-IN USER
    // -------------------------------------------------

    @GetMapping("/me")
    public ResponseEntity<UserResponse>
    getCurrentUser(
            Authentication authentication) {

        UserResponse response =
                userService.getCurrentUser(
                        authentication.getName()
                );

        return ResponseEntity.ok(response);
    }
}