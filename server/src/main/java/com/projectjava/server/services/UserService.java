package com.projectjava.server.services;

import com.projectjava.server.models.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    List<User> getUsers();

    User createUser(User user);

    void deleteUser(Integer id);

    User updateUser(Integer id, User user);

    Integer getId(String username);
}
