package com.aslanbaris.pethouse.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE, reason = "Email User already exists!")
public class EmailUserAlreadyExistException extends Exception {
    public EmailUserAlreadyExistException() {
        super("Email User already exists!");
    }
}
