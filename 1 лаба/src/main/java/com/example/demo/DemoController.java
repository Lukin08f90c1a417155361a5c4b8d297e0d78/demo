package com.example.demo; 

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping("/text")
    public String getText() {
        return "Привет! Это тестовый текст из Spring Boot.";
    }

    @GetMapping("/numbers")
    public int getNumbers() {
        return 42;
    }
}
