package com.projectjava.server.services;

import com.projectjava.server.models.entities.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class RoommateMatchingServiceImpl implements RoommateMatchingService {
    private final StudentService studentService;
    private Map<String, Set<String>> preferences = new HashMap<>();
    private Map<String, String> receivedProposal = new HashMap<>();
    private Map<String, String> sentProposal = new HashMap<>();
    private Set<String> noProposalSentPeople = new HashSet<>();
    private Set<String> peopleLeftUnmatched = new HashSet<>();

    @Autowired
    public RoommateMatchingServiceImpl(StudentService studentService) {
        this.studentService = studentService;
    }

    //TODO 
    @Override
    public Map<Student, Student> generateRoommateMatching() {
        getAllPreferences();
        //return runIrvingAlgorithm();
        return null;
    }

    private void getAllPreferences() {

    }

//    private Map<Student, Student> runIrvingAlgorithm() {
//
//    }
}
