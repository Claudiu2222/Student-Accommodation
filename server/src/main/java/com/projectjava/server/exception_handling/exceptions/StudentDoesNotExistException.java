package com.projectjava.server.exception_handling.exceptions;

public class StudentDoesNotExistException extends RuntimeException {
    public StudentDoesNotExistException(Integer userID) {
        super("Student with id = " + userID + " does not exist");
    }
}
