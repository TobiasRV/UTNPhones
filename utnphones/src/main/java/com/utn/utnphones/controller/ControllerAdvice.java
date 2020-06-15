package com.utn.utnphones.controller;

import com.utn.utnphones.dto.ErrorResponseDto;
import com.utn.utnphones.exceptions.*;
import org.hibernate.HibernateException;
import org.springframework.dao.DataIntegrityViolationException;
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

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(BillNotFoundException.class)
    public ErrorResponseDto handleBillNotFound() {
        return new ErrorResponseDto(2, "Bill not found");
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(LineNotFoundException.class)
    public ErrorResponseDto handleLineNotFound() {
        return new ErrorResponseDto(3, "Line not found");
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ProvinceNotFoundException.class)
    public ErrorResponseDto handleProvinceNotFound() {
        return new ErrorResponseDto(4, "Province not found");
    }


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CityNotFoundException.class)
    public ErrorResponseDto handleCityNotFound() {
        return new ErrorResponseDto(4, "City not found");
    }


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(RateNotFoundException.class)
    public ErrorResponseDto handleRateNotFound() {
        return new ErrorResponseDto(4, "Rate not found");
    }

    /*
        409 CONFLICT
        The request could not be completed due to a conflict with the current state of the target resource.
        This code is used in situations where the user might be able to resolve the conflict and resubmit the request.
     */
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(ValidationException.class)
    public ErrorResponseDto handleValidationException(ValidationException e) {
        return new ErrorResponseDto(5, e.getMessage());
    }

    /*
        422 UNPROCESSABLE ENTITY
        The server understands the content type of the request entity, and thesyntax of the request entity is correct
        but was unable to process the contained instructions.
     */
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(HibernateException.class)
    public ErrorResponseDto handleHibernateError(RuntimeException e) {
        return new ErrorResponseDto(6, e.getCause().getCause().getMessage());
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ErrorResponseDto handleUniqueConstraint() {
        return new ErrorResponseDto(7, "One or more fields are not valid");
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(InvalidLoginException.class)
    public ErrorResponseDto handleInvalidLogin() {
        return new ErrorResponseDto(9, "Username or password are not valid");
    }
}























