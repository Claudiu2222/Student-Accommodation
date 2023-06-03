package com.projectjava.server.exception_handling.exceptions;

public class StudentCannotBeHisOwnRoommateException extends RuntimeException {
    public StudentCannotBeHisOwnRoommateException(Integer userId) {
        super("Student with id = " + userId + " cannot be his own roommate");
    }
}
