package com.icommerce.app.shopping.user.service.rest.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.icommerce.app.shopping.user.service.rest.request.UserLoginRequest;
import io.swagger.annotations.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Api(value = "v1", description = "the v1 API")
public interface V1Api {

    @ApiOperation(value = "User login.", nickname = "userLogin", notes = "User login", response = String.class, tags = {"User",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User login successfully.", response = String.class),
            @ApiResponse(code = 400, message = "Bad request, validation error"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    @RequestMapping(value = "/v1/users/login", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<String> userLogin(
            @ApiParam(value = "The user login request data as JSON.", required = true) @Valid @RequestBody UserLoginRequest userLoginRequest)
            throws JsonProcessingException;

    @ApiOperation(value = "Get current user.", nickname = "getCurrentUser", notes = "Get current user", response = String.class, tags = {"User",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Get user successfully.", response = String.class),
            @ApiResponse(code = 400, message = "Bad request, validation error"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    @RequestMapping(value = "/v1/users/me", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<String> getCurrentUser(@ApiParam(value = "Authorization Token.", required = true) @Valid @RequestHeader(name = HttpHeaders.AUTHORIZATION) String token)
            throws JsonProcessingException;
}