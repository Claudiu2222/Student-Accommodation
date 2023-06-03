package com.projectjava.server.controllers;


import com.projectjava.server.models.entities.Student;
import com.projectjava.server.services.RoommateMatchingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(path = "/roommate-matching")
public class RoommateMatchingController {

    private final RoommateMatchingService roommateMatchingService;

    @Autowired
    public RoommateMatchingController(RoommateMatchingService roommateMatchingService) {
        this.roommateMatchingService = roommateMatchingService;
    }

    @GetMapping
    public ResponseEntity<Map<Student, Student>> generateRoommateMatching() {
        Map<Student, Student> roommateMatching = roommateMatchingService.getRoommateMatchings();
        return ResponseEntity.ok(roommateMatching);

    }
}
