package com.ibroximjon.spring_rest.controller;

import com.ibroximjon.spring_rest.dto.LoginRequest;
import com.ibroximjon.spring_rest.dto.LoginResponse;
import com.ibroximjon.spring_rest.security.JwtUtil;
import com.ibroximjon.spring_rest.service.AuthService;
import com.ibroximjon.spring_rest.service.LoginAttemptService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;


@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication")
public class AuthController {


    private final AuthService authService;


    private final JwtUtil jwtUtil;
    private final LoginAttemptService loginAttemptService;



    private final AuthenticationManager authenticationManager;

    AuthController(AuthService authService, JwtUtil jwtUtil, AuthenticationManager authenticationManager, LoginAttemptService loginAttemptService) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.loginAttemptService = loginAttemptService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        String username = request.getUsername();

        if (loginAttemptService.isBlocked(username)) {
            long minutesLeft = loginAttemptService.minutesLeft(username);
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                    .body("🚫 User is temporarily blocked. Try again in " + minutesLeft + " minute(s).");
        }

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, request.getPassword())
            );

            loginAttemptService.loginSucceeded(username); // ✅ reset on success

            String token = jwtUtil.generateToken(username);
            return ResponseEntity.ok(new LoginResponse(token));

        } catch (AuthenticationException e) {
            loginAttemptService.loginFailed(username); // ❌ count on failure
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("❌ Invalid credentials");
        }
    }

    @PutMapping("/change-password")
    @Operation(description = "Change password")
    public ResponseEntity<String> changePassword(@RequestParam String username,
                                                 @RequestParam String oldPassword,
                                                 @RequestParam String newPassword) {
        boolean changed = authService.changePassword(username, oldPassword, newPassword);
        return changed
                ? ResponseEntity.ok("Password changed successfully")
                : ResponseEntity.badRequest().body("Old password is incorrect or user not found");
    }
}
