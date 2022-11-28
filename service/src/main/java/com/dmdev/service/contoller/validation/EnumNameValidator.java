package com.dmdev.service.contoller.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class EnumNameValidator implements ConstraintValidator<EnumName, Enum<?>> {

    private Pattern pattern;

    @Override
    public void initialize(EnumName constraintAnnotation) {
        try {
            pattern = Pattern.compile(constraintAnnotation.regexp());
        } catch (PatternSyntaxException e) {
            throw new IllegalArgumentException("Given regex is invalid", e);
        }
    }

    @Override
    public boolean isValid(Enum<?> anEnum, ConstraintValidatorContext constraintValidatorContext) {
        if (anEnum == null) {
            return true;
        }
        Matcher m = pattern.matcher(anEnum.name());
        return m.matches();
    }
}
