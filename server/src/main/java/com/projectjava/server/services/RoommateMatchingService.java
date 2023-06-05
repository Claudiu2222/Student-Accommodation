package com.projectjava.server.services;


import com.projectjava.server.models.entities.Matching;
import com.projectjava.server.models.entities.Student;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface RoommateMatchingService {
    void generateRoommateMatching();

    List<Matching> getRoommateMatchings();


    Student getRoommateMatchingOfStudent(Integer studentId);

    Integer getRoommateMatchingsCount();

    void deleteRoommateMatching();
}
