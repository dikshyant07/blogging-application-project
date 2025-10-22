package com.blogging.application.bloggingProject.validators;

import com.blogging.application.bloggingProject.enums.Gender;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GenderValidatorContraint implements ConstraintValidator<GenderValidator, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        List<Gender> genders = List.of(Gender.values());
        return genders.stream().anyMatch(gender -> gender.name().equals(s));
    }
}
