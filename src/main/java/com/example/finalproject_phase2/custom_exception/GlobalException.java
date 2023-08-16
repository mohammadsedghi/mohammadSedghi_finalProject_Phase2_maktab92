package com.example.finalproject_phase2.custom_exception;

import com.example.finalproject_phase2.dto.addressDto.AddressDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.example.finalproject_phase2.dto.ProjectResponse;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice

public class GlobalException extends ResponseEntityExceptionHandler {
//        @ExceptionHandler(CustomException.class)
//        public static ResponseEntity<String> handlerDeleteAddressException(CustomException ce, WebRequest webRequest){
//            return new ResponseEntity<>(ce.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
    @ExceptionHandler(CustomException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleAddressNotFoundException(CustomException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("aaaaaae"+ex.getMessage());
    }
}
