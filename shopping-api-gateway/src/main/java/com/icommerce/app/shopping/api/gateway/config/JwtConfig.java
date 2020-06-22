package com.icommerce.app.shopping.api.gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "security.jwt")
public class JwtConfig {
    @Value("${uri:/auth/**}")
    private String uri;

    @Value("${header:Authorization}")
    private String header;

    @Value("${prefix:Bearer }")
    private String prefix;

    @Value("${expiration:#{24*60*60}}")
    private int expiration;

    @Value("${secret:JwtSecretKey}")
    private String secret;

    private String[] ignoreFilters;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public int getExpiration() {
        return expiration;
    }

    public void setExpiration(int expiration) {
        this.expiration = expiration;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String[] getIgnoreFilters() {
        return ignoreFilters;
    }

    public void setIgnoreFilters(String[] ignoreFilters) {
        this.ignoreFilters = ignoreFilters;
    }
}