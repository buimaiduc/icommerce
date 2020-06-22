package com.icommerce.app.shopping.common.rest.exception;

public class BaseException extends RuntimeException {
    private static final long serialVersionUID = -8279292782665815881L;

    private final String errorCode;

    private final String errorMessage;

    private final transient Object details;

    public BaseException() {
        this.errorMessage = null;
        this.errorCode = null;
        this.details = null;
    }

    public BaseException(final String errorCode, final String message) {
        super(message);
        this.errorMessage = message;
        this.errorCode = errorCode;
        this.details = null;
    }

    public BaseException(final String errorCode, final String message, final Object details) {
        super(message);
        this.errorMessage = message;
        this.errorCode = errorCode;
        this.details = details;
    }

    public BaseException(final String errorCode, final Throwable throwable) {
        super(throwable);
        this.errorCode = errorCode;
        this.errorMessage = throwable.getMessage();
        this.details = null;
    }

    public BaseException(final String errorCode, final String message, final Throwable throwable) {
        super(message, throwable);
        this.errorCode = errorCode;
        this.errorMessage = message;
        this.details = null;
    }

    public BaseException(final String errorCode, final String message, final Object details, final Throwable throwable) {
        super(message, throwable);
        this.errorCode = errorCode;
        this.errorMessage = message;
        this.details = details;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public Object getDetails() {
        return this.details;
    }

}
