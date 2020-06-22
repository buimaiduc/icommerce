package com.icommerce.app.shopping.user.service.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.icommerce.app.shopping.common.authentication.service.TokenGenerator;
import com.icommerce.app.shopping.common.rest.exception.ResourceNotFoundException;
import com.icommerce.app.shopping.user.service.model.User;
import com.icommerce.app.shopping.user.service.repository.UserRepository;
import com.icommerce.app.shopping.user.service.rest.response.UserLoginResponse;
import com.icommerce.app.shopping.user.service.service.api.ExternalFacebookApi;
import com.icommerce.app.shopping.user.service.service.api.model.FacebookAccountResponse;
import com.icommerce.app.shopping.user.service.service.impl.UserServiceImpl;
import feign.Request;
import feign.Response;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpHeaders;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    private static final String FACEBOOK_TOKEN = "qhnNDk2AOK";
    private static final String GENERATED_TOKEN_1 = "6OwOgQ5hJX";
    private static final String GENERATED_TOKEN_2 = "mQgo4G11Xi";
    private static final long USER_ID = 123131;
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private ExternalFacebookApi externalFacebookApi;
    @Mock
    private UserRepository userRepository;
    @Mock
    private TokenGenerator tokenGenerator;
    @Mock
    private ObjectMapper mapper;

    @Test(expected = ResourceNotFoundException.class)
    public void testLoginWithFacebookException() {
        Map<String, Collection<String>> headers = new HashMap<>();
        Response.Builder builder = Response.builder();
        headers.put(HttpHeaders.CONTENT_TYPE, Collections.singletonList("application/json"));
        builder.request(Request.create(Request.HttpMethod.GET, "http", headers, "test".getBytes(), Charset.defaultCharset()));
        Response response = builder.body("response", Charset.defaultCharset()).headers(headers).status(400).build();
        when(externalFacebookApi.getUser(any(), any())).thenReturn(response);
        userService.loginWithFacebook(FACEBOOK_TOKEN);
    }

    @Test
    public void testLoginWithFacebook() throws IOException {
        Map<String, Collection<String>> headers = new HashMap<>();
        Response.Builder builder = Response.builder();
        headers.put(HttpHeaders.CONTENT_TYPE, Collections.singletonList("application/json"));
        builder.request(Request.create(Request.HttpMethod.GET, "http", headers, "test".getBytes(), Charset.defaultCharset()));
        String body = "{\n" +
                "\"email\": \"email@gmail.com\",\n" +
                "\"name\": \"Account\",\n" +
                "\"id\": \"300161231265589841\"\n" +
                "}";
        Response response = builder.body(body, Charset.defaultCharset()).headers(headers).status(200).build();

        FacebookAccountResponse facebookAccountResponse = new FacebookAccountResponse();
        facebookAccountResponse.setId(300161231265589841L);
        facebookAccountResponse.setEmail("email@gmail.com");
        facebookAccountResponse.setName("Account");

        User user = new User();
        user.setId(300161231265589841L);
        user.setEmail("email@gmail.com");
        user.setName("Account");

        when(externalFacebookApi.getUser(any(), any())).thenReturn(response);
        when(mapper.readValue(body, FacebookAccountResponse.class)).thenReturn(facebookAccountResponse);

        when(userRepository.findByEmail(any())).thenReturn(null);
        when(userRepository.save(any())).thenReturn(user);
        when(tokenGenerator.generate(any(), anyInt(), any())).thenReturn(GENERATED_TOKEN_1);

        UserLoginResponse userLoginResponse = userService.loginWithFacebook(FACEBOOK_TOKEN);
        Assert.assertEquals(userLoginResponse.getToken(), GENERATED_TOKEN_1);
    }

    @Test
    public void testLoginWithFacebookWithExistingUser() throws IOException {
        Map<String, Collection<String>> headers = new HashMap<>();
        Response.Builder builder = Response.builder();
        headers.put(HttpHeaders.CONTENT_TYPE, Collections.singletonList("application/json"));
        builder.request(Request.create(Request.HttpMethod.GET, "http", headers, "test".getBytes(), Charset.defaultCharset()));
        String body = "{\n" +
                "\"email\": \"email@gmail.com\",\n" +
                "\"name\": \"Account\",\n" +
                "\"id\": \"300161231265589841\"\n" +
                "}";
        Response response = builder.body(body, Charset.defaultCharset()).headers(headers).status(200).build();

        FacebookAccountResponse facebookAccountResponse = new FacebookAccountResponse();
        facebookAccountResponse.setId(300161231265589841L);
        facebookAccountResponse.setEmail("email@gmail.com");
        facebookAccountResponse.setName("Account");

        User user = new User();
        user.setId(300161231265589841L);
        user.setEmail("email@gmail.com");
        user.setName("Account");

        when(externalFacebookApi.getUser(any(), any())).thenReturn(response);
        when(mapper.readValue(body, FacebookAccountResponse.class)).thenReturn(facebookAccountResponse);

        when(userRepository.findByEmail(any())).thenReturn(user);
        when(tokenGenerator.generate(any(), anyInt(), any())).thenReturn(GENERATED_TOKEN_2);

        UserLoginResponse userLoginResponse = userService.loginWithFacebook(FACEBOOK_TOKEN);
        Assert.assertEquals(userLoginResponse.getToken(), GENERATED_TOKEN_2);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void findByIdWithException() {
        userService.findById(USER_ID);
    }

    @Test
    public void findById() {
        User user = new User();
        user.setId(USER_ID);
        user.setEmail("email@gmail.com");
        user.setName("Account");
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));
        Assert.assertNotNull(userService.findById(USER_ID));
    }
}
