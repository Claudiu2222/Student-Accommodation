package com.example.services;

import com.example.data.Credential;

public interface LoginService {
    Integer checkCredentials();

    void checkIfUsernameExist();

    void changePassword(String username, String oldPassword, String password);

    String getRole();

    void setCredential(Credential credential);

    Credential getCredential();
}
