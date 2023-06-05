package com.projectjava.server.services;

import com.projectjava.server.models.dtos.UserStudentDTO;
import com.projectjava.server.models.entities.Student;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StudentService {
    List<Student> getStudents();

    void createStudent(UserStudentDTO newStudent);

    Student getStudent(Integer userID);

    void deleteStudent(Integer userID);

    void createDummys(Integer numberOfDummies);
}
