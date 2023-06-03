package com.projectjava.server.controllers;

import com.projectjava.server.models.dtos.UserStudentDTO;
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
        studentService.createDummys(numberOfDummies);
    }

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(studentService.getStudents());
    }

    @GetMapping(path = "/{userID}")
    public ResponseEntity<Student> getSpecificStudent(@PathVariable Integer userID) {
        return ResponseEntity.ok(studentService.getStudent(userID));
    }

    @PostMapping
    public void createStudent(@RequestBody UserStudentDTO newStudent) {
        studentService.createStudent(newStudent);
    }

    @DeleteMapping(path = "/{userID}")
    public void deleteStudent(@PathVariable Integer userID) {
        studentService.deleteStudent(userID);
    }
}
