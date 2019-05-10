package com.safaricom.movie.auth;

import com.safaricom.movie.utils.Status;
import com.safaricom.movie.utils.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public final class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    public static final Logger log = LoggerFactory.getLogger(CustomAuthenticationEntryPoint.class);

    public CustomAuthenticationEntryPoint() {
    }

    @Override
    public void commence(final HttpServletRequest request, final HttpServletResponse response, final AuthenticationException authException) throws IOException {
            log.warn("User: FAILED");

        response.setHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE);

        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        response.getOutputStream().println(Util.toJson(new Status(403,authException.getMessage())));
    }
}

