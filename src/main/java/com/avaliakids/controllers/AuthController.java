package com.avaliakids.controllers;

import com.avaliakids.models.User;
import com.avaliakids.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        try {
            authService.registerUser(user.getName(), user.getEmail(), user.getPassword(), user.getRole());
            return ResponseEntity.ok("Usuário registrado com sucesso");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password) {
        Optional<User> authenticatedUser = authService.authenticateUser(email, password);
        if (authenticatedUser.isPresent()) {
            String token = authService.generateToken(email);
            return ResponseEntity.ok(Map.of("token", token));
        } else {
            return ResponseEntity.status(401).body("Credenciais inválidas");
        }
    }
}
