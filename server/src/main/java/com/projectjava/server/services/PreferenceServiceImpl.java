package com.projectjava.server.services;

import com.projectjava.server.exception_handling.exceptions.StudentCannotBeHisOwnRoommateException;
import com.projectjava.server.exception_handling.exceptions.StudentDoesNotExistException;
import com.projectjava.server.models.entities.Preference;
import com.projectjava.server.models.entities.Student;
import com.projectjava.server.repositories.PreferenceRepository;
import com.projectjava.server.repositories.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PreferenceServiceImpl implements PreferenceService {

    private final PreferenceRepository preferenceRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public PreferenceServiceImpl(PreferenceRepository preferenceRepository, StudentRepository studentRepository) {
        this.preferenceRepository = preferenceRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public void createPreference(Integer studentId, Integer roommatePreferenceId, Integer rank) {
        Student stud1 = studentRepository.findById(studentId).orElseThrow(() -> new StudentDoesNotExistException(studentId));
        Student stud2 = studentRepository.findById(roommatePreferenceId).orElseThrow(() -> new StudentDoesNotExistException(roommatePreferenceId));
        if (stud1.getUser_id().equals(stud2.getUser_id()))
            throw new StudentCannotBeHisOwnRoommateException(stud1.getUser_id());
        preferenceRepository.save(new Preference(stud1, stud2, rank));
    }

    @Transactional
    @Override
    public void deletePreferencesOfStudent(Integer studentId) {
        Student stud = studentRepository.findById(studentId).orElseThrow(() -> new StudentDoesNotExistException(studentId));
        preferenceRepository.deleteAllByStudentId(stud.getUser_id());
    }

    @Override
    public List<Preference> getPreferences() {
        return preferenceRepository.findAll();
    }

    @Override
    public List<Preference> getPreferencesOfStudent(Integer studentId) {
        Student stud = studentRepository.findById(studentId).orElseThrow(() -> new StudentDoesNotExistException(studentId));
        return preferenceRepository.findAllByStudentId(studentId);
    }
}
