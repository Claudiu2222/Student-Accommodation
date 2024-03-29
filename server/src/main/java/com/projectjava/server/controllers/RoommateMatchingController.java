package com.projectjava.server.controllers;


import com.projectjava.server.models.entities.Matching;
import com.projectjava.server.models.entities.Student;
import com.projectjava.server.services.RoommateMatchingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/roommate-matching")
public class RoommateMatchingController {

    private final RoommateMatchingService roommateMatchingService;

    @Autowired
    public RoommateMatchingController(RoommateMatchingService roommateMatchingService) {
        this.roommateMatchingService = roommateMatchingService;
    }

    @PostMapping(path = "/generate")
    public void generateRoommateMatching() {
        roommateMatchingService.generateRoommateMatching();
    }

    @GetMapping
    public ResponseEntity<List<Matching>> getRoommateMatchings() {
        return ResponseEntity.ok(roommateMatchingService.getRoommateMatchings());
    }

    @GetMapping(path = "{studentId}")
    public ResponseEntity<Student> getRoommateMatchingOfStudent(@PathVariable Integer studentId) {
        Student roommateMatching = roommateMatchingService.getRoommateMatchingOfStudent(studentId);
        return ResponseEntity.ok(roommateMatching);
    }

    @GetMapping(path = "/count")
    public ResponseEntity<Integer> getNumberOfRoommateMatchings() {
        Integer numberOfRoommateMatchings = roommateMatchingService.getRoommateMatchingsCount();
        return ResponseEntity.ok(numberOfRoommateMatchings);
    }

    @DeleteMapping
    public void deleteRoommateMatching() {
        roommateMatchingService.deleteRoommateMatching();
    }
}
