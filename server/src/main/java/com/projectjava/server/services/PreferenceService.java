package com.projectjava.server.services;


import com.projectjava.server.models.entities.Preference;
import com.projectjava.server.models.entities.Student;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public interface PreferenceService {
    public void createPreference(Integer studentId, List<Student> preferencesOfStudent);

    public void deletePreferencesOfStudent(Integer studentId);

    List<Preference> getPreferences();

    Set<Student> getPreferencesOfStudent(Integer studentId);
}
