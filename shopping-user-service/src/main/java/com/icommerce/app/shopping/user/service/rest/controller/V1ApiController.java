package com.icommerce.app.shopping.user.service.rest.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.icommerce.app.shopping.common.authentication.authentication.User;
import com.icommerce.app.shopping.common.authentication.service.CurrentUserService;
import com.icommerce.app.shopping.user.service.rest.request.UserLoginRequest;
import com.icommerce.app.shopping.user.service.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/")
public class V1ApiController implements V1Api {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private CurrentUserService currentUserService;

    @Value("${app.security.jwt.key}")
    private String jwtKey;

    @Override
    public ResponseEntity<String> userLogin(@Valid UserLoginRequest userLoginRequest) throws JsonProcessingException {
        if (userLoginRequest == null || StringUtils.isEmpty(userLoginRequest.getFacebookToken())) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return ResponseEntity.ok(objectMapper.writeValueAsString(userService.loginWithFacebook(userLoginRequest.getFacebookToken())));
    }

    @Override
    public ResponseEntity<String> getCurrentUser(@Valid String token) throws JsonProcessingException {
        Long userId = 0L;
        try {
            userId = this.getUserId(token);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return ResponseEntity.ok(objectMapper.writeValueAsString(userService.findById(userId)));
    }

    private Long getUserId(String authorizationToken) throws JsonProcessingException {
        final User currentUserLogin = objectMapper.readValue(currentUserService.getCurrentUserLogin(authorizationToken, jwtKey), User.class);
        return currentUserLogin != null ? currentUserLogin.getId() : 0L;
    }
}