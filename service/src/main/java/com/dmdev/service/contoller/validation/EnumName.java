package com.dmdev.service.contoller.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(
        validatedBy = EnumNameValidator.class
)
public @interface EnumName {

    String regexp();

    String message() default "must match \"{regexp}\"";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
