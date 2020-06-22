package com.icommerce.app.shopping.product.service.rest.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.icommerce.app.shopping.product.service.rest.request.BrandRequest;
import com.icommerce.app.shopping.product.service.rest.request.ProductRequest;
import com.icommerce.app.shopping.product.service.rest.request.SearchRequest;
import com.icommerce.app.shopping.product.service.rest.request.UpdateProductRequest;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

public interface V1Api {
    @ApiOperation(value = "Save a new products.", nickname = "insertProduct", notes = "Insert product", response = String.class, tags = {"Product",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Insert brand successfully.", response = String.class),
            @ApiResponse(code = 400, message = "Bad request, validation error"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    @RequestMapping(value = "/v1/products", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<String> insertProduct(@ApiParam(value = "The product request data as JSON.", required = true) @Valid @RequestBody ProductRequest productRequest)
            throws JsonProcessingException;

    @ApiOperation(value = "Search products.", nickname = "searchProducts", notes = "Search products", response = String.class, tags = {"Product",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Search products successfully.", response = String.class),
            @ApiResponse(code = 400, message = "Bad request, validation error"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    @RequestMapping(value = "/v1/products/search", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<String> searchProducts(@ApiParam(value = "The search request data as JSON.", required = true) @Valid @RequestBody SearchRequest searchRequest)
            throws JsonProcessingException;

//    @ApiOperation(value = "Get products with paging.", nickname = "getProducts", notes = "Get products with paging", response = String.class, tags = {"Product",})
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Get products successfully.", response = String.class),
//            @ApiResponse(code = 400, message = "Bad request, validation error"),
//            @ApiResponse(code = 500, message = "Internal Server Error")})
//    @RequestMapping(value = "/v1/products", produces = {"application/json"}, method = RequestMethod.GET)
//    ResponseEntity<String> getProductsWithPaging(@ApiParam(value = "Product Id", required = true, example = "123") @RequestParam("ids") List<Long> ids)
//            throws JsonProcessingException;

    @ApiOperation(value = "Get products by ids.", nickname = "getProducts", notes = "Get products", response = String.class, tags = {"Product",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Get products successfully.", response = String.class),
            @ApiResponse(code = 400, message = "Bad request, validation error"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    @RequestMapping(value = "/v1/products", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<String> getProducts(@ApiParam(value = "Product Id", required = true, example = "123") @RequestParam("ids") List<Long> ids)
            throws JsonProcessingException;

    @ApiOperation(value = "Get product by id.", nickname = "getProduct", notes = "Get product by id", response = String.class, tags = {"Product",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Get product successfully.", response = String.class),
            @ApiResponse(code = 400, message = "Bad request, validation error"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    @RequestMapping(value = "/v1/products/{id}", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<String> getProduct(@ApiParam(value = "Product Id", required = true, example = "123") @PathVariable("id") Long id)
            throws JsonProcessingException;

    @ApiOperation(value = "Delete product.", nickname = "deleteProduct", notes = "Delete product", response = String.class, tags = {"Product",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Delete product successfully.", response = String.class),
            @ApiResponse(code = 400, message = "Bad request, validation error"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    @RequestMapping(value = "/v1/products/{id}", produces = {"application/json"}, method = RequestMethod.DELETE)
    ResponseEntity<String> deleteProduct(@ApiParam(value = "Product Id", required = true, example = "123") @PathVariable("id") Long id);

    @ApiOperation(value = "Update product.", nickname = "updateProduct", notes = "Update product", response = String.class, tags = {"Product",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Update product successfully.", response = String.class),
            @ApiResponse(code = 400, message = "Bad request, validation error"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    @RequestMapping(value = "/v1/products/{id}", produces = {"application/json"}, method = RequestMethod.PUT)
    ResponseEntity<String> updateProduct(@ApiParam(value = "Product Id", required = true, example = "123") @PathVariable("id") Long id,
                                         @ApiParam(value = "The update product data as JSON.", required = true) @Valid @RequestBody UpdateProductRequest updateProductRequest)
            throws JsonProcessingException;

    @ApiOperation(value = "Save a new brand.", nickname = "insertBrand", notes = "Insert brand", response = String.class, tags = {"Brand",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Insert brand successfully.", response = String.class),
            @ApiResponse(code = 400, message = "Bad request, validation error"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    @RequestMapping(value = "/v1/brands", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<String> insertBrand(@ApiParam(value = "The brand data as JSON.", required = true) @Valid @RequestBody BrandRequest brandRequest)
            throws JsonProcessingException;

    @ApiOperation(value = "Get brands.", nickname = "getBrands", notes = "Get brands", response = String.class, tags = {"Brand",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Get brands successfully.", response = String.class),
            @ApiResponse(code = 400, message = "Bad request, validation error"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    @RequestMapping(value = "/v1/brands", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<String> getBrands() throws JsonProcessingException;


    @ApiOperation(value = "Get price tracking by product id.", nickname = "getProductPrices", notes = "Get price tracking by product id.", response = String.class, tags = {"Product",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Get product successfully.", response = String.class),
            @ApiResponse(code = 400, message = "Bad request, validation error"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    @RequestMapping(value = "/v1/products/{id}/prices", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<String> getProductPrices(@ApiParam(value = "Product Id", required = true, example = "123") @PathVariable("id") Long id)
            throws JsonProcessingException;
}
