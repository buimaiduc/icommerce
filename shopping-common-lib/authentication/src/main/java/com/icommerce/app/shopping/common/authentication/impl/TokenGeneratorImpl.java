package com.icommerce.app.shopping.common.authentication.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.icommerce.app.shopping.common.authentication.authentication.User;
import com.icommerce.app.shopping.common.authentication.service.TokenBuilder;
import com.icommerce.app.shopping.common.authentication.service.TokenGenerator;
import org.springframework.security.crypto.codec.Base64;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

public class TokenGeneratorImpl implements TokenGenerator {

    private final Algorithm algorithm;

    public TokenGeneratorImpl(final String key) throws IOException {
        this.algorithm = Algorithm.HMAC256(key);
    }

    @Override
    public String generate(final User user, final int expiredTime, final TimeUnit timeUnit) {
        final JWTCreator.Builder jwtBuilder = JWT.create();
        final TokenBuilder tokenBuilder = new TokenBuilder(jwtBuilder);
        tokenBuilder.expiresTime(expiredTime, timeUnit);
        final byte[] jwtSubBytes = user.toString().getBytes(StandardCharsets.UTF_8);
        final String jwtSub = new String(Base64.encode(jwtSubBytes), StandardCharsets.UTF_8);
        tokenBuilder.subject(jwtSub);
        return jwtBuilder.sign(algorithm);
    }
}