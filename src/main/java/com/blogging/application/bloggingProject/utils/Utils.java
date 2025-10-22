package com.blogging.application.bloggingProject.utils;

import com.blogging.application.bloggingProject.enums.Gender;
import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Component
@Data
@ConfigurationProperties(prefix = "utils")
public class Utils {
    private Admin admin;
    private Manager manager;
    private Jwt jwt;

    @Data
    @Getter
    public static class Admin {
        private String name;
        private int age;
        private Gender gender;
        private String email;
        private String password;
    }

    @Data
    @Getter
    public static class Manager {
        private String name;
        private int age;
        private Gender gender;
        private String email;
        private String password;
    }

    @Data
    @Getter
    public static class Jwt {
        private String secret;
        private int expiry;
    }
}
