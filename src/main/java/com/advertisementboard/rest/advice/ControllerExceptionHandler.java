package com.advertisementboard.rest.advice;

import com.advertisementboard.exception.entity.EntityException;
import com.advertisementboard.exception.role.NoPrivilegeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(EntityException.class)
    public ResponseEntity<?> entityExceptionHandler(final EntityException exception){
        log.info(exception.getMessage());
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @ExceptionHandler(NoPrivilegeException.class)
    public ResponseEntity<?> noPrivilegeExceptionHandler(final NoPrivilegeException exception){
        log.info(exception.getMessage());
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.FORBIDDEN);
    }

}
