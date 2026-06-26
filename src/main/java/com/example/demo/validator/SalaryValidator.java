package com.example.demo.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SalaryValidator implements ConstraintValidator<ValidSalary, Object> {

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        if (value == null) {
            addMessage(context, "Salary is required");
            return false;
        }

        if (!(value instanceof String)) {
            addMessage(context, "Salary must be sent as String");
            return false;
        }

        String str = (String) value;

        if (str.isBlank()) {
            addMessage(context, "Salary cannot be empty");
            return false;
        }

        // ✅ Check numeric format
        if (!str.matches("^[0-9]+(\\.[0-9]{1,2})?$")) {
            addMessage(context, "Salary must be numeric");
            return false;
        }

        // ✅ Positive check
        double salary = Double.parseDouble(str);
        if (salary <= 0) {
            addMessage(context, "Salary must be greater than zero");
            return false;
        }

        return true;
    }

    private void addMessage(ConstraintValidatorContext context, String message) {

        context.disableDefaultConstraintViolation();

        context.buildConstraintViolationWithTemplate(message)
               .addConstraintViolation();
    }
}