/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safaricom.movie.repository;

import com.safaricom.movie.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author david
 */
public interface  UserDao extends JpaRepository<User, Integer>{
    boolean existsByEmail(String email);
}
