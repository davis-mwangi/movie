/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safaricom.movie.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safaricom.movie.model.UserModel;
import com.safaricom.movie.utils.Util;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

/**
 *
 * @author david
 */
@Component
public class AuthenticationFilterBean extends GenericFilterBean {
    private static final Logger log = LoggerFactory.getLogger(AuthenticationFilterBean.class);
    private static final String HEADER_STRING = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";
    public static final String SB_FILE_PATH = "/usr/local/safaricom/files/";
    private static final String PUBLIC_KEY_FILENAME = SB_FILE_PATH+"safaricom_public_key.der";

    /**
     *
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = ((HttpServletRequest) request);
        String token = httpRequest.getHeader(HEADER_STRING);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(token != null) {
            try {
                //Encode the claims using the public key 
                Claims claims = Jwts.parser()
                        .setSigningKey(Util.getPublicKey(PUBLIC_KEY_FILENAME))
                        .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                        .getBody();

                UserModel userModel = new ObjectMapper().convertValue(claims.get("user"), UserModel.class);
                log.error("claims: {}", userModel);
                if (userModel != null) {
                    List<GrantedAuthority> authorities = new ArrayList<>();
                
                    authentication = new UsernamePasswordAuthenticationToken(new ApiPrincipal(userModel), null, authorities);
                }
            }catch (JwtException e){
              authentication = null;
            }
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }
}
