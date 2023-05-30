package com.projectjava.server.services;

import com.projectjava.server.models.entities.User;
import com.projectjava.server.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    //trb sa schimbam mai tarziu nu vom returna useri ci useridto ( fara pass inclusa ). Va trebui modificat peste tot dar pana cand avem cv stabil
    //lasam asa
    public List<User> getUsers() {
        return  userRepository.findAll();
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    public User updateUser(Integer id, User user) {
        User userToUpdate = userRepository.findById(id).orElseThrow(()-> new RuntimeException("User not found"));
        userToUpdate.setUsername(user.getUsername());
        userToUpdate.setPassword(user.getPassword());
        userToUpdate.setRole(user.getRole());
        return userRepository.save(userToUpdate);

    }
}
