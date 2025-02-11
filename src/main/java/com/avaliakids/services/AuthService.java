package com.avaliakids.services;

import com.avaliakids.exceptions.InvalidRoleException;
import com.avaliakids.exceptions.UserAlreadyExistsException;
import com.avaliakids.models.User;
import com.avaliakids.repositories.UserRepository;
import com.avaliakids.utils.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.jwtUtil = jwtUtil;
    }

    public User registerUser(String name, String email, String password, String role) {
        if (!(role.equalsIgnoreCase("PARENT") || role.equalsIgnoreCase("TEACHER"))) {
            throw new InvalidRoleException("O papel especificado é inválido. Deve ser PARENT ou TEACHER.");
        }

        Optional<User> existingUser = userRepository.findByEmail(email);
        if (existingUser.isPresent()) {
            throw new UserAlreadyExistsException("O e-mail fornecido já está registrado.");
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role.toUpperCase().trim());

        return userRepository.save(user);
    }

    public Optional<User> authenticateUser(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            return user;
        }
        return Optional.empty();
    }

    public String generateToken(String email) {
        return jwtUtil.generateToken(email);
    }
}
