/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safaricom.movie;

import com.safaricom.movie.payload.UserRequest;
import com.safaricom.movie.repository.UserDao;
import com.safaricom.movie.service.UserService;
import com.safaricom.movie.utils.Response;
import com.safaricom.movie.utils.SingleItemResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author david
 */
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private UserDao userDao;
    
    @PostMapping
    private ResponseEntity createUser(@RequestBody UserRequest request){
        boolean exists = userDao.existsByEmail(request.getEmail());
        
        if(exists){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new SingleItemResponse(Response.USER_EXISTS.status(), null));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUpdateUser(request));
    }
    
    @PutMapping
    private ResponseEntity updateUser(@RequestBody UserRequest request){
        boolean exists = userDao.existsById(request.getId());
        
        if(!exists){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new SingleItemResponse(Response.USER_NOT_FOUND.status(), null));
        }
        return ResponseEntity.status(HttpStatus.OK).body(userService.createUpdateUser(request));
    }
    
}
