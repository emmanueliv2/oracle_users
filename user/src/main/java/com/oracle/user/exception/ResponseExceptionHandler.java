package com.oracle.user.exception;


import com.oracle.user.exception.list.BadCredentialsException;
import com.oracle.user.exception.list.InternalErrorException;
import com.oracle.user.exception.list.NotFoundException;
import com.oracle.user.exception.model.ExceptionModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@ControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> err = ex.getBindingResult().getFieldErrors().stream().map(fieldError -> fieldError.getField().concat( " cannot been empty")).collect(Collectors.toList());
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(InternalErrorException.class)
    public final ResponseEntity<Object> handleInternalException(InternalErrorException ex, WebRequest request){
        ExceptionModel exceptionModel = new ExceptionModel("50000", "Internal Error", ex.getMessage(),new Date(),new Date());
        return new ResponseEntity<>(exceptionModel, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(BadCredentialsException.class)
    public final ResponseEntity<Object> handleCredentialsException(BadCredentialsException ex, WebRequest request){
        ExceptionModel exceptionModel = new ExceptionModel("40000", "Bad Request", ex.getMessage(),new Date(),new Date());
        return new ResponseEntity<>(exceptionModel, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity<Object> handleNotFoundException(NotFoundException ex, WebRequest request){
        ExceptionModel exceptionModel = new ExceptionModel("40400", "Not Found", ex.getMessage(),new Date(),new Date());
        return new ResponseEntity<>(exceptionModel, HttpStatus.NOT_FOUND);
    }
}
