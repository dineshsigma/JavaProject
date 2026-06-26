package com.example.demo.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})   // ✅ only for fields
@Retention(RetentionPolicy.RUNTIME) // ✅ available at runtime
@Constraint(validatedBy = SalaryValidator.class) // ✅ link validator
@Documented
public @interface ValidSalary {

    String message() default "Salary must be a valid numeric string";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}