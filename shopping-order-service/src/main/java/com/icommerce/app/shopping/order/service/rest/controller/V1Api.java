package com.icommerce.app.shopping.order.service.rest.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.icommerce.app.shopping.order.service.rest.request.OrderRequest;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

public interface V1Api {
    @ApiOperation(value = "Save a new order.", nickname = "insertOrder", notes = "Insert order", response = String.class, tags = {"Order",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Insert order successfully.", response = String.class),
            @ApiResponse(code = 400, message = "Bad request, validation error"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    @RequestMapping(value = "/v1/orders", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<String> insertOrder(
            @ApiParam(value = "The order data as JSON.", required = true) @Valid @RequestBody OrderRequest orderRequest,
            @ApiParam(value = "Authorization Token.", required = true) @Valid @RequestHeader(name = HttpHeaders.AUTHORIZATION) String token)
            throws JsonProcessingException;

    @ApiOperation(value = "Save a new order.", nickname = "insertOrder", notes = "Insert order", response = String.class, tags = {"Order",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Insert order successfully.", response = String.class),
            @ApiResponse(code = 400, message = "Bad request, validation error"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    @RequestMapping(value = "/v1/orders/{orderId}", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<String> getOrder(
            @ApiParam(value = "Order Id", required = true, example = "123") @Valid @PathVariable(value = "orderId") Long orderId,
            @ApiParam(value = "Authorization Token.", required = true) @Valid @RequestHeader(name = HttpHeaders.AUTHORIZATION) String token)
            throws JsonProcessingException;
}
