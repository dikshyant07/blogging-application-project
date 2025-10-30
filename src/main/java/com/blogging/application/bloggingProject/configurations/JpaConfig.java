package com.blogging.application.bloggingProject.configurations;

import com.blogging.application.bloggingProject.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class JpaConfig {
    private final UserRepository userRepository;

    public JpaConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    public AuditorAware<Long> auditorAware() {
        return new AuditingConfig(userRepository);
    }
}
