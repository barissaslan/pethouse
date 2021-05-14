package com.aslanbaris.pethouse.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE, reason = "Invalid email.")
public class InvalidEmailException extends Exception {
    public InvalidEmailException(String email) {
        super(email + " is an invalid email.");
    }
}
