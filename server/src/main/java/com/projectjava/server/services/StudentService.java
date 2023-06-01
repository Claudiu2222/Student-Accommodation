package com.projectjava.server.services;

import com.projectjava.server.models.dtos.StudentDTO;
import com.projectjava.server.models.dtos.UserStudentDTO;
import com.projectjava.server.models.entities.Student;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StudentService {
    public List<StudentDTO> getStudents();

    public void createStudent(UserStudentDTO newStudent);

    public StudentDTO getStudent(Integer userID);

    public void deleteStudent(Integer userID);

    public void createDummys(Integer numberOfDummies);
}
