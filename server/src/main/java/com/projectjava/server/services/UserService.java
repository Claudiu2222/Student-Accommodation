package com.projectjava.server.services;

import com.projectjava.server.models.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    public List<User> getUsers();

    public User createUser(User user);

    public void deleteUser(Integer id);

    public User updateUser(Integer id, User user);
}
