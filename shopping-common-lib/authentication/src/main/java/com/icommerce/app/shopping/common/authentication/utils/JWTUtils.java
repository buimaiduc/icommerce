package com.icommerce.app.shopping.common.authentication.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.UnsupportedEncodingException;

public final class JWTUtils {

    public static final String HEADER_TOKEN_PREFIX = "Bearer ";

    private JWTUtils() {
        // default constructor
    }

    public static DecodedJWT verifyToken(final String token, final String key) throws UnsupportedEncodingException {
        final Algorithm algorithm = Algorithm.HMAC256(key);
        final JWTVerifier verifier = JWT.require(algorithm).build();
        return verifier.verify(token);
    }

    public static String extractJwt(final String header) {
        return header.substring(HEADER_TOKEN_PREFIX.length());
    }
}
