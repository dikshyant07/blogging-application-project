package com.blogging.application.bloggingProject.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = GenderValidatorContraint.class)
public @interface GenderValidator {
    String message() default "Gender must me MALE,FEMALE or OTHERS";

    Class<?>[] fields() default {};

    Class<? extends Payload>[] payload() default {};
}
