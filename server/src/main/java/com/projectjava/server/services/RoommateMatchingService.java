package com.projectjava.server.services;


import com.projectjava.server.models.entities.Student;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface RoommateMatchingService {
    void generateRoommateMatching();

    Map<Student, Student> getRoommateMatchings();


}
