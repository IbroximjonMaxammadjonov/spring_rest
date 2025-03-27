package com.ibroximjon.spring_rest.service;

public interface AuthService {
    boolean login(String username, String password);
    boolean changePassword(String username, String oldPassword, String newPassword);
}



