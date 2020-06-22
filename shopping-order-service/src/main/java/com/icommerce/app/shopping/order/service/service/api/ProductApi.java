package com.icommerce.app.shopping.order.service.service.api;

import feign.Response;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RefreshScope
@FeignClient(name = "${app.api.product.service.name}", contextId = "ProductApi")
public interface ProductApi {

    @GetMapping(value = "/v1/products", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Response getProducts(@RequestParam("ids") List<Long> ids);
}