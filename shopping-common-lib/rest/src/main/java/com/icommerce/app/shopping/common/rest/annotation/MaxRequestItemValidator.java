package com.icommerce.app.shopping.common.rest.annotation;

import com.icommerce.app.shopping.common.rest.exception.InvalidParameterException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
@RefreshScope
public class MaxRequestItemValidator implements ConstraintValidator<ValidInputNumber, Integer> {

    @Value("${maxRequestSize: 200}")
    private int maxRequestSize;

    @Override
    public void initialize(final ValidInputNumber validInputNumber) {
        // nothing
    }

    @Override
    public boolean isValid(final Integer number, final ConstraintValidatorContext constraintValidatorContext) {
        if (number > maxRequestSize) {
            throw new InvalidParameterException("Input parameter must be in allowance range");
        }

        return true;
    }
}
