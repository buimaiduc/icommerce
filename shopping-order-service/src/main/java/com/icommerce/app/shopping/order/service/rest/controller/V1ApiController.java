package com.icommerce.app.shopping.order.service.rest.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.icommerce.app.shopping.common.authentication.authentication.User;
import com.icommerce.app.shopping.common.authentication.service.CurrentUserService;
import com.icommerce.app.shopping.order.service.rest.request.OrderRequest;
import com.icommerce.app.shopping.order.service.service.OrderService;
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

    @Value("${app.security.jwt.key}")
    private String jwtKey;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private OrderService orderService;

    @Autowired
    private CurrentUserService currentUserService;

    @Override
    public ResponseEntity<String> insertOrder(@Valid OrderRequest orderRequest, @Valid String token) throws JsonProcessingException {
        Long userId = 0L;
        try {
            userId = this.getUserId(token);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return ResponseEntity.ok(objectMapper.writeValueAsString(orderService.insert(userId, orderRequest)));
    }

    @Override
    public ResponseEntity<String> getOrder(@Valid Long orderId, @Valid String token) throws JsonProcessingException {
        Long userId = 0L;
        try {
            userId = this.getUserId(token);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return ResponseEntity.ok(objectMapper.writeValueAsString(orderService.findById(orderId, userId)));
    }

    private Long getUserId(String authorizationToken) throws JsonProcessingException {
        final User currentUserLogin = objectMapper.readValue(currentUserService.getCurrentUserLogin(authorizationToken, jwtKey), User.class);
        return currentUserLogin != null ? currentUserLogin.getId() : 0L;
    }
}