package com.example.lostfound.controller;

import java.util.Collections;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.lostfound.model.User;
import com.example.lostfound.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Убрали CsrfTokenRepository, чтобы не было ошибки
    public AuthController(UserRepository userRepository, 
                          PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        if (user.getPassword() == null || user.getPassword().length() < 6) {
            return ResponseEntity.badRequest().body("Пароль слишком короткий");
        }
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Пользователь уже существует");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Collections.singleton("ROLE_USER"));
        user.setEnabled(true);
        userRepository.save(user);
        return new ResponseEntity<>("Пользователь успешно зарегистрирован", HttpStatus.CREATED);
    }

    // Забираем токен прямо из запроса (Spring Security сам его туда кладет)
    @GetMapping("/csrf")
    public ResponseEntity<Map<String, String>> getCsrfToken(HttpServletRequest request) {
        CsrfToken token = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        if (token != null) {
            return ResponseEntity.ok(Map.of("token", token.getToken()));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Токен не найден"));
    }
}