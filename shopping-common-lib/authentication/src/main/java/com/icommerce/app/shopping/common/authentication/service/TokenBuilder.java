package com.icommerce.app.shopping.common.authentication.service;

import com.auth0.jwt.JWTCreator;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TokenBuilder {

    private final JWTCreator.Builder builder;

    public TokenBuilder(final JWTCreator.Builder builder) {
        this.builder = builder;
    }

    public TokenBuilder issuer(final String issuer) {
        this.builder.withIssuer(issuer);
        return this;
    }

    public TokenBuilder subject(final String subject) {
        this.builder.withSubject(subject);
        return this;
    }

    public TokenBuilder expiresTime(final int expiredTime, final TimeUnit timeUnit) {
        final long millis = System.currentTimeMillis() + timeUnit.toMillis(expiredTime);
        this.builder.withExpiresAt(new Date(millis));
        return this;
    }

    public TokenBuilder claim(final String name, final String value) {
        this.builder.withClaim(name, value);
        return this;
    }

    public TokenBuilder claim(final String name, final Boolean value) {
        this.builder.withClaim(name, value);
        return this;
    }

    public TokenBuilder claim(final String name, final Long value) {
        this.builder.withClaim(name, value);
        return this;
    }

    public TokenBuilder claim(final String name, final Date value) {
        this.builder.withClaim(name, value);
        return this;
    }
}