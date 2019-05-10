/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safaricom.movie.service.implementation;

import com.safaricom.movie.entities.User;
import com.safaricom.movie.payload.UserRequest;
import com.safaricom.movie.repository.UserDao;
import com.safaricom.movie.service.UserService;
import com.safaricom.movie.utils.Response;
import com.safaricom.movie.utils.SingleItemResponse;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 *
 * @author david
 */
@Service
public class UserServiceImpl implements UserService{
    
    @Autowired
    private UserDao userDao;

    @Override
    public SingleItemResponse createUpdateUser(UserRequest request) {
        User user = new User();
        Date  now =  new Date ();
        user.setEmail(request.getEmail());
        if(request.getId() != null){
            user =  userDao.getOne(request.getId());
            user.setDateLastUpdated(now);
            user.setEmail(user.getEmail());
        }
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setPhysicalAddress(request.getPhysicalAddress());
        user.setCity(request.getCity());
        user.setCountry(request.getCountry());
        user.setDateCreated(now);
        
        userDao.save(user);
        return  new SingleItemResponse(Response.SUCCESS.status(), user);
    }

    @Override
    public SingleItemResponse getUser(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SingleItemResponse deleteUser(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
