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
    public Integer checkCredentials(Credentials credentials) {
        User user = userRepository.findUserByUsername(credentials.username());

        if (user == null || !user.getPassword().equals(credentials.password())) {
            throw new ResponseStatusException(
                    HttpStatusCode.valueOf(404), "Error while searching for the user! Wrong credentials!"
            );
        }

        if (user.getFirstTime()) {
            throw new ResponseStatusException(
                    HttpStatusCode.valueOf(201)
            );
        }

        return user.getId();
    }

    @Override
    public void checkIfUsernameExist(String username) {
        User user = userRepository.findUserByUsername(username);

        if (user == null) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Error while searching for username! Wrong credentials!");
        }
    }

    @Override
    public void changePassword(String username, String oldPassword, String password) {
        User user = userRepository.findUserByUsername(username);

        if (user == null || !user.getPassword().equals(oldPassword)) {
            throw new ResponseStatusException(
                    HttpStatusCode.valueOf(404), "Error while searching for the user! Wrong credentials!"
            );
        }

        user.setPassword(password);
        user.setFirstTime(false);
        userRepository.save(user);
    }

    @Override
    public String getRole(String username) {
        User user = userRepository.findUserByUsername(username);

        if (user == null) {
            throw new ResponseStatusException(
                    HttpStatusCode.valueOf(404), "Error while searching for the user! Wrong credentials!"
            );
        }

        if (user.getRole() == 1) {
            return "admin";
        } else {
            return "student";
        }
    }
}
