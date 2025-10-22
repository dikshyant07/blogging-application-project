package com.blogging.application.bloggingProject.utils;

import com.blogging.application.bloggingProject.enums.Gender;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class Utils {
    public static class Admin {
        private String name;
        private int age;
        private Gender gender;
        private String email;
        private String password;
    }

    private static class Manager {
        private String name;
        private int age;
        private Gender gender;
        private String email;
        private String password;
    }

    private static class Jwt {
        private String secret;
        private int expiry;
    }
}
