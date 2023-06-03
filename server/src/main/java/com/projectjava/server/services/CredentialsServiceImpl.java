package com.projectjava.server.services;

import com.projectjava.server.models.entities.Credentials;
import com.projectjava.server.models.entities.User;
import com.projectjava.server.repositories.UserRepository;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CredentialsServiceImpl implements CredentialsService {
    public final UserRepository userRepository;

    public CredentialsServiceImpl(UserRepository userRepository) {
      this.userRepository = userRepository;
    }
    @Override
    public void checkCredentials(Credentials credentials) {
        User user = userRepository.findUserByUsername(credentials.username());

        if (user == null || !user.getPassword().equals(credentials.password())) {
            throw new ResponseStatusException(
                    HttpStatusCode.valueOf(404), "Error while searching for the user! Wrong credentials!"
            );
        }
    }

    @Override
    public void checkIfUsernameExist(String username) {
        User user = userRepository.findUserByUsername(username);

        if (user == null) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Error while searching for username! Wrong credentials!");
        }
    }
}
