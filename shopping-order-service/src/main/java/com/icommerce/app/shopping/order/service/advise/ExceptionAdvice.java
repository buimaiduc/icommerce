package com.icommerce.app.shopping.order.service.advise;

import com.icommerce.app.shopping.common.rest.exception.ApiError;
import com.icommerce.app.shopping.common.rest.exception.ApiException;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.StringJoiner;

@ControllerAdvice
public class ExceptionAdvice {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionAdvice.class);

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiError> handleInternalServerException(ApiException ex) {
        LOGGER.error("Error encounter: {}", ex.getMessage());
        ApiError apiError = ex.getApiError();
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<String> feignException(FeignException ex) {
        LOGGER.error("Un-expected Feign error.", ex);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE);
        HttpStatus httpStatus;
        try {
            httpStatus = HttpStatus.valueOf(ex.status());
        } catch (IllegalArgumentException e) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        StringJoiner message = new StringJoiner("\n");
        message.add(ex.getMessage());
        return new ResponseEntity<>(message.toString(), httpHeaders, httpStatus);
    }


}