package com.projectjava.server.services;

import com.projectjava.server.models.entities.Credentials;
import org.springframework.stereotype.Service;

@Service
public interface CredentialsService {
    public void checkCredentials(Credentials credentials);
    public void checkIfUsernameExist(String username);
}
