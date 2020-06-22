package com.icommerce.app.shopping.common.rest.exception;

public class ApiException extends RuntimeException {
    private ApiError apiError;

    public ApiException(ApiError apiError) {
        super(apiError.getMessage());
        this.apiError = apiError;
    }

    public ApiError getApiError() {
        return apiError;
    }
}
