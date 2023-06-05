package com.projectjava.server.exception_handling.exceptions;

public class CannotChooseRoommateAfterMatchingsGeneratedException extends RuntimeException {
    public CannotChooseRoommateAfterMatchingsGeneratedException() {
        super("Cannot choose roommate after matchings generated");
    }
}
