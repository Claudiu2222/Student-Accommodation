package com.example.services;

import com.example.entities.Student;

import java.io.IOException;
import java.util.List;

public interface AdminService {
    List<Student> getStudents();
    void deleteStudent(Student student) throws Exception;
    void resetPassword(Student student) throws Exception;
    void deletePreference(Student student) throws Exception;
    void deleteMatchings() throws Exception;
    void generateMatchings() throws Exception;
    Student getPariedStudent(Student selectedStudent) throws IOException;
}
