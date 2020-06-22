package com.icommerce.app.shopping.user.service.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.icommerce.app.shopping.common.authentication.service.TokenGenerator;
import com.icommerce.app.shopping.common.rest.exception.ApiError;
import com.icommerce.app.shopping.common.rest.exception.ApiException;
import com.icommerce.app.shopping.common.rest.exception.ResourceNotFoundException;
import com.icommerce.app.shopping.user.service.model.User;
import com.icommerce.app.shopping.user.service.repository.UserRepository;
import com.icommerce.app.shopping.user.service.rest.response.UserLoginResponse;
import com.icommerce.app.shopping.user.service.service.UserService;
import com.icommerce.app.shopping.user.service.service.api.ExternalFacebookApi;
import com.icommerce.app.shopping.user.service.service.api.model.FacebookAccountResponse;
import feign.Response;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
@RefreshScope
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private ExternalFacebookApi facebookApi;

    @Autowired
    private ObjectMapper mapper;

    @Value("${app.authen.facebook.extractFields}")
    private String extractFields;

    @Value("${app.authen.facebook.expiredTokenMinutes:10}")
    private int expiredTokenMinutes;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenGenerator tokenGenerator;

    @Override
    public UserLoginResponse loginWithFacebook(String facebookToken) {
        Response response = facebookApi.getUser(extractFields, facebookToken);
        if (response.status() >= 400) {
            LOGGER.error("Not found facebook token: {}", facebookToken);
            throw new ResourceNotFoundException("Facebook Identity not found");
        }

        try {
            FacebookAccountResponse facebookAccountResponse = mapper.readValue(IOUtils.toString(response.body().asInputStream()), FacebookAccountResponse.class);
            User user = userRepository.findByEmail(facebookAccountResponse.getEmail());

            if (user != null) {
                com.icommerce.app.shopping.common.authentication.authentication.User authenticationUser = new com.icommerce.app.shopping.common.authentication.authentication.User();
                authenticationUser.setId(user.getId());
                authenticationUser.setName(user.getName());
                authenticationUser.setName(user.getEmail());
                return new UserLoginResponse(tokenGenerator.generate(authenticationUser, expiredTokenMinutes, TimeUnit.MINUTES));
            } else {
                user = new User();
                user.setEmail(facebookAccountResponse.getEmail());
                user.setExternalId(facebookAccountResponse.getId());
                user.setCreatedDate(new Date());

                user = userRepository.save(user);

                com.icommerce.app.shopping.common.authentication.authentication.User authenticationUser = new com.icommerce.app.shopping.common.authentication.authentication.User();
                authenticationUser.setId(user.getId());
                authenticationUser.setName(user.getName());
                authenticationUser.setName(user.getEmail());
                return new UserLoginResponse(tokenGenerator.generate(authenticationUser, expiredTokenMinutes, TimeUnit.HOURS));
            }
        } catch (IOException ex) {
            throw new ApiException(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), ""));
        }
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User " + id + " not found"));
    }
}
