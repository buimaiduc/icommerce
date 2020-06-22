package com.icommerce.app.shopping.order.service.advise;

import com.icommerce.app.shopping.common.rest.exception.ApiError;
import com.icommerce.app.shopping.common.rest.exception.ApiException;
import feign.FeignException;
import feign.codec.EncodeException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@RunWith(MockitoJUnitRunner.class)
public class ExceptionControllerAdviceTest {

    @InjectMocks
    private ExceptionAdvice exceptionAdvice;

    @Test
    public void testFeignException() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE);
        FeignException ex = new EncodeException("error");
        Assert.assertNotNull(exceptionAdvice.feignException(ex));
    }

    @Test
    public void testHandleInternalServerException() {
        ApiException apiException = new ApiException(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "message", ""));
        Assert.assertNotNull(exceptionAdvice.handleInternalServerException(apiException));
    }
}