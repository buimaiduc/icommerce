package com.icommerce.app.shopping.user.service.service.api;

import feign.Response;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RefreshScope
@FeignClient(name = "${app.api.facebook.service.name}", contextId = "ExternalFacebookApi")
public interface ExternalFacebookApi {

    @GetMapping(value = "/me", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Response getUser(@RequestParam("fields") String fields, @RequestParam("access_token") String token);
}