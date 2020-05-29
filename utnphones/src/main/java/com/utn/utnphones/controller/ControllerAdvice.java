package com.utn.utnphones.controller;

import com.utn.utnphones.dto.ErrorResponseDto;
import com.utn.utnphones.exceptions.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;

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

    /*
    The 422 (Unprocessable Entity) status code means the server
    understands the content type of the request entity, and the
    syntax of the request entity is correct but was unable to process the contained instructions.
     */
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ErrorResponseDto handleUniqueConstraint() {
        return new ErrorResponseDto(6, "One or more fields are not valid");
    }

}























