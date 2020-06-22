package com.icommerce.app.shopping.common.authentication.filter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.icommerce.app.shopping.common.authentication.authentication.User;
import com.icommerce.app.shopping.common.authentication.exception.UnAuthorizedException;
import com.icommerce.app.shopping.common.authentication.utils.JWTUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenAuthenticationFilter.class);
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String secretKey;

    public JwtTokenAuthenticationFilter(final String secretKey) {
        this.secretKey = secretKey;
    }

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain) throws ServletException, IOException {

        final String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith(JWTUtils.HEADER_TOKEN_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            final String token = JWTUtils.extractJwt(header);
            final DecodedJWT jwt = JWTUtils.verifyToken(token, secretKey);
            final byte[] jwtSubjectBytes = jwt.getSubject().getBytes(StandardCharsets.UTF_8);
            final String jwtSubject = new String(Base64.decode(jwtSubjectBytes), StandardCharsets.UTF_8);
            final User user = objectMapper.readValue(jwtSubject, User.class); // user service create token with subject is an user

            final TokenAuthentication authentication = new TokenAuthentication(token, user);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);

        } catch (final JWTVerificationException e) {
            LOGGER.error("Invalid access token", e);
            response.sendError(HttpStatus.UNAUTHORIZED.value(), e.getLocalizedMessage());
        } catch (final Throwable e) {
            LOGGER.error("Internal server error", e);
            throw new UnAuthorizedException("Invalid token", e);
        }
    }
}