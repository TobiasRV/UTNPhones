package com.utn.utnphones.controller;

import com.utn.utnphones.dto.ErrorResponseDto;
import com.utn.utnphones.exceptions.CityNotExistsException;
import com.utn.utnphones.exceptions.UserAlreadyExistsException;
import com.utn.utnphones.exceptions.UserNotExistsException;
import com.utn.utnphones.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ControllerAdvice extends ResponseEntityExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public ErrorResponseDto handleUserNotFound() {
        return new ErrorResponseDto(1, "User not found");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserNotExistsException.class)
    public ErrorResponseDto handleUserNotExists() {
        return new ErrorResponseDto(2, "User not exists");
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ErrorResponseDto handleUserAlreadyExists() {
        return new ErrorResponseDto(3, "User already exists");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CityNotExistsException.class)
    public ErrorResponseDto handleCityNotExists() {
        return new ErrorResponseDto(4, "City not Exists");
    }



}























