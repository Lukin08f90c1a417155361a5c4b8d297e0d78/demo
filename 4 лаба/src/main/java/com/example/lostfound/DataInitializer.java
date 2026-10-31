package com.example.lostfound;

import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.lostfound.model.LossReport;
import com.example.lostfound.model.User;
import com.example.lostfound.repository.LossReportRepository;
import com.example.lostfound.repository.UserRepository;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(UserRepository userRepository, 
                                      LossReportRepository lossReportRepository, 
                                      PasswordEncoder passwordEncoder) {
        return args -> {
            System.out.println("=== ЗАПУСК DATA INITIALIZER ===");
            System.out.println("=== КОЛИЧЕСТВО ПОЛЬЗОВАТЕЛЕЙ В БАЗЕ: " + userRepository.count() + " ===");
            
            if (userRepository.count() == 0) {
                // 1. Создаем пользователей
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("password"));
                admin.setName("Администратор");
                admin.setEnabled(true);
                admin.setRoles(Set.of("ROLE_ADMIN"));
                userRepository.save(admin);

                User user = new User();
                user.setUsername("user");
                user.setPassword(passwordEncoder.encode("password"));
                user.setName("Иван Иванов");
                user.setEnabled(true);
                user.setRoles(Set.of("ROLE_USER"));
                user = userRepository.save(user); // сохраняем и запоминаем

                System.out.println("=== ПОЛЬЗОВАТЕЛИ УСПЕШНО СОЗДАНЫ ===");

                // 2. Создаем заявления о пропаже для этого пользователя
                LossReport report1 = new LossReport();
                report1.setApplicant(user);
                report1.setDescription("Потерял кошелек в парке");
                report1.setCategory("Кошелек");
                report1.setStatus("OPEN");
                lossReportRepository.save(report1);

                LossReport report2 = new LossReport();
                report2.setApplicant(user);
                report2.setDescription("Потерял телефон в метро");
                report2.setCategory("Телефон");
                report2.setStatus("OPEN");
                lossReportRepository.save(report2);

                System.out.println("=== ЗАЯВЛЕНИЯ УСПЕШНО СОЗДАНЫ ===");
            }
        };
    }
}
