package com.norma.restweather.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Slf4j
public class ControllerAdvice extends ResponseEntityExceptionHandler {
    //There is just a ControllerAdvice Class in the Project

    /*
    @ExceptionHandler(WeatherControllerException.class)
    public String nullPointerHandled(NullPointerException exception){
        log.info("null pointer exception!!!");
        return exception.getMessage();
    }
    */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        String message = ex.getMessage();

        return ResponseEntity.badRequest().body(message);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        log.info("handledMissingServletRequestParameter -->"+ex.getParameterName()+" -->"+ex.getParameterType());
        return super.handleMissingServletRequestParameter(ex, headers, status, request);
    }


}
