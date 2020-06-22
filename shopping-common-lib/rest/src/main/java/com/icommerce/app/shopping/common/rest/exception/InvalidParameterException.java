package com.icommerce.app.shopping.common.rest.exception;

public class InvalidParameterException extends BaseException {
    private static final long serialVersionUID = 6319701242162237710L;
    private static final String ERROR_CODE = "41002";

    public InvalidParameterException(final String message) {
        super(ERROR_CODE, message);
    }
}