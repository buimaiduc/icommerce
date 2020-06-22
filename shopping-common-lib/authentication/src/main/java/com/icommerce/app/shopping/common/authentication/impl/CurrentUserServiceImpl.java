package com.icommerce.app.shopping.common.authentication.impl;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.icommerce.app.shopping.common.authentication.authentication.User;
import com.icommerce.app.shopping.common.authentication.exception.UnAuthorizedException;
import com.icommerce.app.shopping.common.authentication.filter.TokenAuthentication;
import com.icommerce.app.shopping.common.authentication.service.CurrentUserService;
import com.icommerce.app.shopping.common.authentication.utils.JWTUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.codec.Base64;

import java.nio.charset.StandardCharsets;

public class CurrentUserServiceImpl implements CurrentUserService {

    @Override
    public User getCurrentUserLogin() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof TokenAuthentication) {
            return (User) authentication.getPrincipal();
        }
        return null;
    }

    @Override
    public String getCurrentUserLogin(String authorizationToken, String secretKey) {
        try {
            String token = JWTUtils.extractJwt(authorizationToken);
            DecodedJWT jwt = JWTUtils.verifyToken(token, secretKey);
            byte[] jwtSubjectBytes = jwt.getSubject().getBytes(StandardCharsets.UTF_8);
            return new String(Base64.decode(jwtSubjectBytes), StandardCharsets.UTF_8);
        } catch (JWTVerificationException var11) {
            throw new UnAuthorizedException("Invalid token", var11);
        } catch (Throwable var12) {
            throw new UnAuthorizedException("Invalid token", var12);
        }
    }
}