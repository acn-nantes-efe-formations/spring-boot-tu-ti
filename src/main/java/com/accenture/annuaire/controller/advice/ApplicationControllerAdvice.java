package com.accenture.annuaire.controller.advice;


import com.accenture.annuaire.exception.PersonneException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Void> methodArgumentTypeMismatchException (Exception e) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({NoResourceFoundException.class, EntityNotFoundException.class})
    public ResponseEntity<Void> entityNotFoundException(Exception e) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({PersonneException.class, MethodArgumentNotValidException.class})
    public ResponseEntity<Void> personneException(Exception e) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


}
