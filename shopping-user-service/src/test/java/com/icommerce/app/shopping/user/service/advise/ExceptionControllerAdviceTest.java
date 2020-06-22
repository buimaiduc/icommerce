package com.icommerce.app.shopping.user.service.advise;

import feign.FeignException;
import feign.codec.EncodeException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpHeaders;
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
}