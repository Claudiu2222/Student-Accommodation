package com.example.services;

import com.example.entities.Student;

import java.io.IOException;
import java.util.List;

public interface StudentPanelService {
    List<Student> getStudents() throws IOException;

    void sendPreferences(List<Student> preferences) throws IOException;

    boolean wereMatchingsGenerated() throws IOException;

    boolean wereUserPreferencesSent() throws IOException;

    Student checkUsersMatch() throws IOException;
    void setUserID(Integer userID);
}
