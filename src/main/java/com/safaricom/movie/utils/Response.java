/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safaricom.movie.utils;

/**
 *
 * @author david
 */

public enum Response {
    SUCCESS(0, "Success"),
    NO_USERS_FOUND(1,"No users found"),
    USER_NOT_FOUND(2, "User not found"),
    USER_EXISTS(3, "User already exists");
    
    private final Status status;
    Response(int code, String message){
        this.status = new Status(code, message);
    }
    
    public Status status(){
        return status;
    }
}
