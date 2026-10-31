package com.example.lostfound.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import com.example.lostfound.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true) // Включает @PreAuthorize
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // Настройка CSRF: используем Cookie, HttpOnly=false для чтения из JS
            .csrf(csrf -> csrf
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .ignoringRequestMatchers("/api/auth/register") // Исключение для регистрации
            )
            // Настройка правил доступа
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/register", "/api/auth/csrf").permitAll() // Регистрация открыта
                .requestMatchers(HttpMethod.GET, "/api/found-items", "/api/loss-reports").hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/found-items").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/loss-reports").hasRole("USER")
                .requestMatchers("/api/item-claims/claim").hasRole("USER")
                .requestMatchers("/api/handover-records/confirm/**").hasRole("ADMIN")
                .requestMatchers("/h2-console/**").permitAll()
                .anyRequest().authenticated()
            )
            // Включаем HTTP Basic
            .httpBasic(basic -> {})
            // Настройка обработки ошибок
            .exceptionHandling(ex -> ex
                .authenticationEntryPoint((request, response, authException) -> {
                    response.setStatus(401);
                    response.setContentType("application/json;charset=UTF-8");
                    response.getWriter().write("{\"error\": \"Требуется аутентификация\"}");
                })
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                    response.setStatus(403);
                    response.setContentType("application/json;charset=UTF-8");
                    response.getWriter().write("{\"error\": \"Доступ запрещен\"}");
                })
            )
            // Отключаем frameOptions для H2-консоли
            .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()));

        return http.build();
    }
}