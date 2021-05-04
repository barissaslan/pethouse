package com.aslanbaris.pethouse.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE, reason = "User already exists!")
public class UserAlreadyExistException extends Exception {
}
