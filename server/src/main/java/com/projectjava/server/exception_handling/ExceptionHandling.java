package com.projectjava.server.exception_handling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.projectjava.server.exception_handling.exceptions.*;

@ControllerAdvice
public class ExceptionHandling {
    @ExceptionHandler({StudentDoesNotExistException.class})
    public ResponseEntity<ErrorResponse> handleException(StudentDoesNotExistException exception) {
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({StudentCannotBeHisOwnRoommateException.class})
    public ResponseEntity<ErrorResponse> handleException(StudentCannotBeHisOwnRoommateException exception) {
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({CannotChooseRoommateAfterMatchingsGeneratedException.class})
    public ResponseEntity<ErrorResponse> handleException(CannotChooseRoommateAfterMatchingsGeneratedException exception) {
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

}
