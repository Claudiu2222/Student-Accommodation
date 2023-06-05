package com.projectjava.server.services;

import com.projectjava.server.exception_handling.exceptions.CannotChooseRoommateAfterMatchingsGeneratedException;
import com.projectjava.server.exception_handling.exceptions.StudentCannotBeHisOwnRoommateException;
import com.projectjava.server.exception_handling.exceptions.StudentDoesNotExistException;
import com.projectjava.server.models.entities.Preference;
import com.projectjava.server.models.entities.Student;
import com.projectjava.server.repositories.PreferenceRepository;
import com.projectjava.server.repositories.RoommateMatchingRepository;
import com.projectjava.server.repositories.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
public class PreferenceServiceImpl implements PreferenceService {

    private final PreferenceRepository preferenceRepository;
    private final StudentService studentService;
    private final RoommateMatchingRepository roommateMatchingRepository;

    @Autowired
    public PreferenceServiceImpl(PreferenceRepository preferenceRepository, StudentService studentService, RoommateMatchingRepository roommateMatchingRepository) {
        this.preferenceRepository = preferenceRepository;
        this.studentService = studentService;
        this.roommateMatchingRepository = roommateMatchingRepository;
    }

    @Transactional
    @Override
    public void createPreference(Integer studentId, List<Student> preferencesOfStudent) {
        if (roommateMatchingRepository.getCount() > 0) {
            throw new CannotChooseRoommateAfterMatchingsGeneratedException();
        }
        Student stud1 = studentService.getStudent(studentId);
        int rank = 1;
        for (Student stud2 : preferencesOfStudent) {
            Student existingStudent = studentService.getStudent(stud2.getUser_id());
            if (stud1.getUser_id().equals(stud2.getUser_id())) {
                throw new StudentCannotBeHisOwnRoommateException(stud1.getUser_id());
            }
            Preference preference = new Preference(stud1, stud2, rank++);
            preferenceRepository.save(preference);
        }
    }

    @Transactional
    @Override
    public void deletePreferencesOfStudent(Integer studentId) {
        Student stud = studentService.getStudent(studentId);
        preferenceRepository.deleteAllByStudentId(stud.getUser_id());
    }

    @Override
    public List<Preference> getPreferences() {
        return preferenceRepository.findAll();
    }

    @Override
    public Set<Student> getPreferencesOfStudent(Integer studentId) {
        Student stud = studentService.getStudent(studentId);
        return new LinkedHashSet<>(preferenceRepository.findAllByStudentId(stud.getUser_id()));
    }
}
