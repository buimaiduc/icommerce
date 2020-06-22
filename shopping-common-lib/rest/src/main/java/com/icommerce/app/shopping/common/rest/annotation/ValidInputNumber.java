package com.icommerce.app.shopping.common.rest.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target({ElementType.PARAMETER, ElementType.FIELD, ElementType.LOCAL_VARIABLE})
@Inherited
@Constraint(validatedBy = {MaxRequestItemValidator.class})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidInputNumber {

    String message() default "Input parameter must be in allowance range";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
