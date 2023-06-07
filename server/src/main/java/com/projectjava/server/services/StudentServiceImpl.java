package com.projectjava.server.services;

import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import com.projectjava.server.exception_handling.exceptions.StudentDoesNotExistException;
import com.projectjava.server.mapper.UserStudentMapper;
import com.projectjava.server.models.dtos.UserStudentDTO;
import com.projectjava.server.models.entities.Student;
import com.projectjava.server.models.entities.User;
import com.projectjava.server.repositories.PreferenceRepository;
import com.projectjava.server.repositories.RoommateMatchingRepository;
import com.projectjava.server.repositories.StudentRepository;
import com.projectjava.server.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final PreferenceRepository preferencesRepository;
    private final RoommateMatchingRepository roommateMatchingRepository;

    public StudentServiceImpl(UserRepository userRepository, StudentRepository studentRepository, PreferenceRepository preferencesRepository, RoommateMatchingRepository roommateMatchingRepository) {
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
        this.preferencesRepository = preferencesRepository;
        this.roommateMatchingRepository = roommateMatchingRepository;
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
        userRepository.deleteById(userID);
        preferencesRepository.deleteAllByStudentId(userID);
        roommateMatchingRepository.deleteAllByStudentId(userID);

    }

    @Override
    public void createDummys(Integer numberOfDummies) {
        for (int i = 1; i <= numberOfDummies; i++) {
            Faker fakeStudent = new Faker();
            Name name = fakeStudent.name();
            String firstName = name.firstName();
            String lastName = name.lastName();

            String nameToUsername = String.format("%s.%s", firstName.toLowerCase(), lastName.toLowerCase());
            var newStudent = UserStudentDTO.builder()
                    .username(nameToUsername)
                    .password(nameToUsername)
                    .firstName(firstName)
                    .lastName(lastName)
                    .groupIn("A4")
                    .yearIn(2)
                    .build();
            this.createStudent(newStudent);
        }
    }
}
