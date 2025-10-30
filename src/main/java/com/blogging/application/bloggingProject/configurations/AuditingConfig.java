package com.blogging.application.bloggingProject.configurations;

import com.blogging.application.bloggingProject.models.User;
import com.blogging.application.bloggingProject.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Slf4j
public class AuditingConfig implements AuditorAware<Long> {
    private final UserRepository userRepository;

    public AuditingConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<Long> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {

            String userName = authentication.getName();
            if (userName != null) {
                User user = userRepository.findByEmail(userName).orElse(null);
                if (user != null) {
                    return Optional.of(user.getId());
                }
            }

        }

        return Optional.empty();
    }
}
