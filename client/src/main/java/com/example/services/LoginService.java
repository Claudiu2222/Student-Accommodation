package com.example.services;

public interface LoginService {
    Integer checkCredentials();
    void checkIfUsernameExist();
    void changePassword(String username, String oldPassword, String password);
    String getRole(String username);
}
