package com.example.finalproject_phase2.custom_exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.example.finalproject_phase2.dto.ProjectResponse;
@RestControllerAdvice
public class GlobalException {
        @ExceptionHandler(RuntimeException.class)
        public static ResponseEntity<ProjectResponse> handlerRunTimeExceptionFailed(RuntimeException re){
            ProjectResponse projectResponse=new ProjectResponse(re.getMessage(),"404");
            return new ResponseEntity<>(projectResponse, HttpStatus.BAD_REQUEST);
        }
}
