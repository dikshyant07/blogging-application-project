package com.blogging.application.bloggingProject.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TagsValidatorConstraint implements ConstraintValidator<TagsValidator, List<String>> {
    @Override
    public boolean isValid(List<String> strings, ConstraintValidatorContext constraintValidatorContext) {
        return !strings.isEmpty();
    }
}
