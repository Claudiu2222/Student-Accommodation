package com.projectjava.server.controllers;

import com.projectjava.server.models.entities.Student;
import com.projectjava.server.services.StudentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/students")
public class StudentController {
    private final StudentServiceImpl studentService;
    @Autowired
    public StudentController(StudentServiceImpl studentService) {
        this.studentService = studentService;
    }
    @PostMapping(path = "/create-dummy={numberOfDummies}")
    public void createDummy(@PathVariable Integer numberOfDummies) {

    }
    @GetMapping(path = "")
    public ResponseEntity<List<Student>> getAllStudents() {
        return studentService.getStudents();
    }
    @GetMapping(path = "{username}")
    public ResponseEntity<Student> getSpecificStudent(@PathVariable String username) {
        return studentService.getStudent(username);
    }
}
