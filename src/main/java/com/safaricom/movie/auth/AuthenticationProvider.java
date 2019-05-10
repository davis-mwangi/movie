package com.safaricom.movie.auth;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;

public class AuthenticationProvider extends DaoAuthenticationProvider {
    public static final Logger log = LoggerFactory.getLogger(CustomAuthenticationEntryPoint.class);
 

    @Override
    public Authentication authenticate(Authentication authentication) {
        Authentication auth = super.authenticate(authentication);
        return auth;
    }
}