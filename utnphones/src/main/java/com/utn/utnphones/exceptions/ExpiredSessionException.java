package com.utn.utnphones.exceptions;

public class ExpiredSessionException extends Exception {
    public ExpiredSessionException(String message) {
        super(message);
    }
}
