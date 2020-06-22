package com.icommerce.app.shopping.common.authentication.exception;

public class UnAuthorizedException extends RuntimeException {

    public UnAuthorizedException(final String msg, final Throwable throwable) {
        super(msg, throwable);
    }

    public UnAuthorizedException(final String msg) {
        super(msg);
    }
}