package com.example.services;

import com.example.entities.Student;

import java.io.IOException;
import java.util.List;

public interface StudentPanelService {
    List<Student> getStudents() throws IOException;
}
