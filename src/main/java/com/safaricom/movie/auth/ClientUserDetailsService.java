/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safaricom.movie.auth;

import com.safaricom.movie.entities.User;
import com.safaricom.movie.repository.UserDao;
import com.safaricom.movie.utils.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author david
 */
@Service
public class ClientUserDetailsService implements UserDetailsService {
    @Autowired
    private UserDao  userDao;

    private static final Logger log = LoggerFactory.getLogger(ClientUserDetailsService.class);

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userDao.findTop1ByUsername(username);
        if (user != null) {
            return new UserPrincipal(user);
        }
        throw new UsernameNotFoundException(Response.USER_NOT_FOUND.status().getMessage());
    }
}
