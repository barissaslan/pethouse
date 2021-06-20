package com.barisaslan.pethouse.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class UserAuthenticationException extends Exception {
    public UserAuthenticationException() {
        super();
    }
}
