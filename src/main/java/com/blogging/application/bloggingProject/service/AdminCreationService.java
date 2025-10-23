package com.blogging.application.bloggingProject.service;

import com.blogging.application.bloggingProject.models.User;
import com.blogging.application.bloggingProject.repository.UserRepository;
import com.blogging.application.bloggingProject.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.blogging.application.bloggingProject.enums.Role.ADMIN;
import static com.blogging.application.bloggingProject.enums.Role.MANAGER;

@Service
public class AdminCreationService implements CommandLineRunner {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final Utils utils;

    @Autowired
    public AdminCreationService(PasswordEncoder passwordEncoder, UserRepository userRepository, Utils utils) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.utils = utils;
    }

    @Override
    public void run(String... args) throws Exception {
        if (!userRepository.existsByEmail(utils.getAdmin().getEmail())) {
            User admin = User.builder()
                    .name(utils.getAdmin().getName())
                    .age(utils.getAdmin().getAge())
                    .gender(utils.getAdmin().getGender())
                    .email(utils.getAdmin().getEmail())
                    .password(passwordEncoder.encode(utils.getAdmin().getPassword()))
                    .role(ADMIN)
                    .build();
            userRepository.save(admin);
        }
        if (!userRepository.existsByEmail(utils.getManager().getEmail())) {
            User manager = User.builder()
                    .name(utils.getManager().getName())
                    .age(utils.getManager().getAge())
                    .gender(utils.getManager().getGender())
                    .email(utils.getManager().getEmail())
                    .password(passwordEncoder.encode(utils.getManager().getPassword()))
                    .role(MANAGER)
                    .build();
            userRepository.save(manager);
        }
    }
}
