package com.projectjava.server.services;

import com.projectjava.server.models.dtos.UserStudentDTO;
import com.projectjava.server.models.entities.Student;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StudentService {
    public ResponseEntity<List<Student>> getStudents();
    public void createStudent(UserStudentDTO newStudent);
    public ResponseEntity<Student> getStudent(String username);
    public void deleteStudent(String username);
    public void createDummys(Integer numberOfDummies);
}
