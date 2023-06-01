package com.projectjava.server.services;

import com.github.javafaker.Faker;
import com.projectjava.server.mapper.StudentMapper;
import com.projectjava.server.models.dtos.UserStudentDTO;
import com.projectjava.server.models.entities.Student;
import com.projectjava.server.models.entities.User;
import com.projectjava.server.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private final UserServiceImpl userService;
    private final UserRepository userRepository;

    public StudentServiceImpl(UserServiceImpl userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<List<Student>> getStudents() {
        return null;
    }

    @Override
    public void createStudent(UserStudentDTO newStudent) {
        User user = StudentMapper.toUser(newStudent);
        user.setId(null); // for creating a new userID
        userRepository.save(user);

        Student student = StudentMapper.toStudent(newStudent);
    }

    @Override
    public ResponseEntity<Student> getStudent(String username) {
        return null;
    }

    @Override
    public void deleteStudent(String username) {

    }

    @Override
    public void createDummys(Integer numberOfDummies) {
        for (int i = 1; i <= numberOfDummies; i++) {
            Faker fakeStudent = new Faker();
        }
    }
}
