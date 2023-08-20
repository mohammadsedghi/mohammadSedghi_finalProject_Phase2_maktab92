package com.example.finalproject_phase2.custom_exception;

import com.example.finalproject_phase2.dto.addressDto.AddressDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.example.finalproject_phase2.dto.ProjectResponse;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestControllerAdvice
public class GlobalException extends ResponseEntityExceptionHandler {
//        @ExceptionHandler(CustomException.class)
//        public static ResponseEntity<String> handlerDeleteAddressException(CustomException ce, WebRequest webRequest){
//            return new ResponseEntity<>(ce.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
    @ExceptionHandler(CustomException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleAddressNotFoundException(CustomException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
    @ExceptionHandler(CustomNoResultException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public ResponseEntity<String> handleNoResultException(CustomNoResultException cre) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(cre.getMessage());
    }
    @ExceptionHandler(CustomNumberFormatException.class)
    public ResponseEntity<String> handleNumberFormatException(CustomNumberFormatException cnfe) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(cnfe.getMessage());
    }
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String,String>> handleInValidException(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        Map<String,String> messages = new HashMap<>();
        for (ConstraintViolation<?> constraintViolation : constraintViolations) {
            messages.put(constraintViolation.getPropertyPath().toString(),constraintViolation.getMessageTemplate());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messages);
    }
}
