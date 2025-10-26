package com.blogging.application.bloggingProject.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TagsValidatorConstraint.class)
public @interface TagsValidator {
    String message() default "Please provide non empty list";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
