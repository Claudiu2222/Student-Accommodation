package com.projectjava.server.services;

import com.projectjava.server.models.entities.Credentials;
import org.springframework.stereotype.Service;

@Service
public interface CredentialsService {
    Integer checkCredentials(Credentials credentials);

    void checkIfUsernameExist(String username);

    Integer changePassword(String username, String oldPassword, String password);

    String getRole(String username);
}
