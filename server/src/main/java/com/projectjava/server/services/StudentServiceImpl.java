package com.projectjava.server.services;

import com.github.javafaker.Faker;
import com.projectjava.server.exception_handling.exceptions.StudentDoesNotExistException;
import com.projectjava.server.mapper.UserStudentMapper;
import com.projectjava.server.models.dtos.UserStudentDTO;
import com.projectjava.server.models.entities.Student;
import com.projectjava.server.models.entities.User;
import com.projectjava.server.repositories.StudentRepository;
import com.projectjava.server.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;

    public StudentServiceImpl(UserRepository userRepository, StudentRepository studentRepository) {
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    @Override
    public void createStudent(UserStudentDTO newStudent) {
        User user = UserStudentMapper.toUser(newStudent);

        Student student = UserStudentMapper.toStudent(newStudent);
        student.setUser_id(userRepository.save(user).getId());
        studentRepository.save(student);
    }

    @Override
    public Student getStudent(Integer userID) {
        Student student = studentRepository.findById(userID).orElseThrow(() -> new StudentDoesNotExistException(userID));
        return student;
    }

    @Override
    public void deleteStudent(Integer userID) {
        studentRepository.deleteById(userID);
        userRepository.deleteById(userID);
    }

    @Override
    public void createDummys(Integer numberOfDummies) {
        for (int i = 1; i <= numberOfDummies; i++) {
            Faker fakeStudent = new Faker();
            var newStudent = UserStudentDTO.builder()
                    .username(fakeStudent.name().username())
                    .password(fakeStudent.internet().password())
                    .firstName(fakeStudent.name().firstName())
                    .lastName(fakeStudent.name().lastName())
                    .groupIn("A4")
                    .yearIn(2)
                    .build();
            this.createStudent(newStudent);
        }
    }
}
