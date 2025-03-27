package com.ibroximjon.spring_rest.controller;

import com.ibroximjon.spring_rest.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication")
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping("/login")
    @Operation(description = "Login with username and password")
    public ResponseEntity<String> login(@RequestParam String username,
                                        @RequestParam String password) {
        boolean success = authService.login(username, password);
        return success
                ? ResponseEntity.ok("Login successful")
                : ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
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
