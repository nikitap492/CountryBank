package com.cbank.controllers;

import com.cbank.exceptions.InsufficientFundsException;
import com.cbank.exceptions.TokenExpiredException;
import com.cbank.exceptions.ValidationException;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;

/**
 * @author Podshivalov N.A.
 * @since 21.11.2017.
 */
@ControllerAdvice
public class ErrorController {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> handle(ValidationException ex){
        return ResponseEntity.badRequest().body(ErrorMessage.create(ex.getMessage()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handle(EntityNotFoundException ex){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<?> handle(TokenExpiredException ex){
        return ResponseEntity.badRequest().body(ErrorMessage.create("The token is expired. Get new token again, please"));
    }

    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<?> handle(InsufficientFundsException ex){
        return ResponseEntity.badRequest().body(ErrorMessage.create("Insufficient funds. Please, fund money on your account"));
    }

    @Data
    @RequiredArgsConstructor(staticName = "create")
    private static class ErrorMessage{
        private final String message;
        private LocalDateTime timestamps = LocalDateTime.now();
    }
}
